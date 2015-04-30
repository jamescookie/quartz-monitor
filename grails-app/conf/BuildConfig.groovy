grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    plugins {

        compile ':quartz:1.0.2'
        compile ':asset-pipeline:2.1.5'

        build ':release:3.1.1', ':rest-client-builder:2.1.1', {
            export = false
        }
    }
}
