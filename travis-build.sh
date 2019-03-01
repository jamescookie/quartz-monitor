#!/usr/bin/env bash
EXIT_STATUS=0

./gradlew --stop

echo "TRAVIS_BRANCH      : $TRAVIS_BRANCH"
echo "TRAVIS_REPO_SLUG   : $TRAVIS_REPO_SLUG"
echo "TRAVIS_PULL_REQUEST: $TRAVIS_PULL_REQUEST"
echo "TRAVIS_TAG         : $TRAVIS_TAG"

rm -rf build

echo "*******************************"
echo "schwartz-monitor:check"
echo "*******************************"
./gradlew :schwartz-monitor:check --no-daemon --console=plain --stacktrace || EXIT_STATUS=$?

if [ $EXIT_STATUS -ne 0 ]; then
  echo "schwartz-monitor:check failed => exit $EXIT_STATUS"
  exit $EXIT_STATUS
fi

echo "*******************************"
echo "schwartz-monitor:install"
echo "*******************************"
./gradlew :schwartz-monitor:install --no-daemon --console=plain --stacktrace || EXIT_STATUS=$?

if [ $EXIT_STATUS -ne 0 ]; then
  echo "schwartz-monitor:install failed => exit $EXIT_STATUS"
  exit $EXIT_STATUS
fi

echo "*******************************"
echo "integration-test-app:check"
echo "*******************************"

./gradlew :integration-test-app:check --no-daemon --console=plain --stacktrace || EXIT_STATUS=$?

if [ $EXIT_STATUS -ne 0 ]; then
  echo "integration-test-app:check failed => exit $EXIT_STATUS"
  exit $EXIT_STATUS
fi


# Only publish if the branch is on master, and not a PR
if [[ -n $TRAVIS_TAG ]] || [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]; then

  if [[ -n $TRAVIS_TAG ]]; then
      echo "Pushing build to Bintray"
      ./gradlew :schwartz-monitor:bintrayUpload || EXIT_STATUS=$?
  fi

  echo "Building Docs"
	./gradlew schwartz-monitor:docs --stacktrace || EXIT_STATUS=$?

	git config --global user.name "$GIT_NAME"
	git config --global user.email "$GIT_EMAIL"
	git config --global credential.helper "store --file=~/.git-credentials"
	echo "https://$GH_TOKEN:@github.com" > ~/.git-credentials

  echo "Cloning gh-pages branch from $TRAVIS_REPO_SLUG"
	git clone https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git -b gh-pages gh-pages --single-branch > /dev/null
	cd gh-pages

  # If this is the master branch then update the snapshot
  if [[ $TRAVIS_BRANCH == 'master' ]]; then

    mv ../plugin/build/docs/ghpages.html index.html
    git add index.html

    mkdir -p snapshot
    cp -r ../plugin/build/docs/. ./snapshot/
    git add snapshot/*
  fi

  # TAG exists > is latest docs
  if [[ -n ${TRAVIS_TAG} ]]; then
        git rm -rf latest/
        mkdir -p latest
        cp -r ../plugin/build/docs/. ./latest/
        git add latest/*

        version="$TRAVIS_TAG" # eg: v3.0.1
        version=${version:1} # 3.0.1
        majorVersion=${version:0:4} # 3.0.
        majorVersion="${majorVersion}x" # 3.0.x

        mkdir -p "$version"
        cp -r ../plugin/build/docs/. "./$version/"
        git add "$version/*"

        git rm -rf "$majorVersion"
        cp -r ../plugin/build/docs/. "./$majorVersion/"
        git add "$majorVersion/*"
  fi

	git commit -a -m "Updating docs for Travis build: https://travis-ci.org/$TRAVIS_REPO_SLUG/builds/$TRAVIS_BUILD_ID"
	echo "Pusing gh-pages to $TRAVIS_REPO_SLUG"
	git push origin gh-pages
  cd ..
  rm -rf gh-pages
fi

exit $EXIT_STATUS