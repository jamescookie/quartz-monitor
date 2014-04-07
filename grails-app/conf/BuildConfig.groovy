grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
    }

    plugins {

        compile ':quartz:1.0.1'

        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
