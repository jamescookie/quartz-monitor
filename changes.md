# Making changes to Quartz Monitor

* Increment version number in build.gradle and add -SNAPSHOT (whilst working on it)
* Make changes
* run `gradle install` to install snapshot version to local maven repo
* go to one of the test projects, e.g. `cd integration-test-app`
* run `gradle run`
* open [quartz monitor page](http://localhost:8080/quartz/list) and make sure jobs are running correctly and the failing job is reported as such
* If everything is ok remove the -SNAPSHOT, commit, tag it (following convention) and push
* publish by running `gradle bintrayUpload`

