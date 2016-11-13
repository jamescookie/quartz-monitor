#!/usr/bin/env bash
EXIT_STATUS=0

./gradlew --stop

echo "TRAVIS_BRANCH: $TRAVIS_BRANCH"
echo "TRAVIS_REPO_SLUG: $TRAVIS_REPO_SLUG"
echo "TRAVIS_PULL_REQUEST: $TRAVIS_PULL_REQUEST"
echo "TRAVIS_TAG: $TRAVIS_TAG"

rm -rf build
./gradlew clean check install --stacktrace || EXIT_STATUS=$?

if [[ $EXIT_STATUS -eq 0 ]]; then
	./integration-test-app/run_integration_tests.sh || EXIT_STATUS=$?
fi

if [[ -n $TRAVIS_TAG && $TRAVIS_PULL_REQUEST == 'false' && $EXIT_STATUS -eq 0 ]]; then
    echo "Uploading to bintray"
	./gradlew bintrayUpload --stacktrace

	./gradlew docs --stacktrace
	git config --global user.name "$GIT_NAME"
	git config --global user.email "$GIT_EMAIL"
	git config --global credential.helper "store --file=~/.git-credentials"
	echo "https://$GH_TOKEN:@github.com" > ~/.git-credentials

    echo "Cloning gh-pages branch from $TRAVIS_REPO_SLUG"
	git clone https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git -b gh-pages gh-pages --single-branch > /dev/null
	cd gh-pages

	rm -rf latest
	mkdir latest
	cp -r ../build/docs/. ./latest/

	mv -f latest/ghpages.html index.html
	git add .

	git commit -a -m "Updating docs for Travis build: https://travis-ci.org/$TRAVIS_REPO_SLUG/builds/$TRAVIS_BUILD_ID"

	echo "Pusing gh-pages to $TRAVIS_REPO_SLUG"
	git push origin gh-pages

fi

exit $EXIT_STATUS