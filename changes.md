#Making changes to Quartz Monitor

* Increment version number in build.gradle and add -SNAPSHOT (whilst working on it)
* Make changes
* run `gradle install` to install snapshot version to local maven
* go to one of the test projects, e.g. `cd src/test/projects/test-harness`
* run `gradle run`
* open [quartz monitor page](http://localhost:8080/quartz/list) and make sure jobs are running correctly and the failing job is reported as such
* If everything is ok remove the -SNAPSHOT, commit, tag it (following convention) and push
* run the bintray-setup commands after editing the api-key that can be found [here](https://bintray.com/profile/edit) > API Key (sign in using github)
* publish by running `gradle bintrayUpload`
* request it's added to https://bintray.com/grails/plugins - or maybe it can be linked after the first time?
