package grails.plugins.schwartzmonitor

import grails.plugins.schwartz.listener.QuartzSchedulerListener

class SchwartzMonitorGrailsPlugin {
    def grailsVersion = "1.3 > *"

    def author = "Robert Oschwald"
    def authorEmail = "robertoschwald@gmail.com"
    def title = "Schwartz Quartz Monitor Grails Plugin"
    def description = 'Enables you to administer all your Schwartz Quartz jobs'

    def documentation = "http://grails.org/plugin/grails-schwartz-monitor"

    def license = "APACHE"
    def scm = [ url: "http://github.com/robertoschwald/grails-schwartz-monitor" ]
    def issueManagement = [ system: "GITHUB", url: "http://github.com/robertoschwald/grails-schwartz-monitor/issues" ]

    def loadAfter = ['schwartz', 'quartz']

    def doWithSpring = {

        monitorSchedulerListener(QuartzSchedulerListener)

        /** Very bad idea to wrap the configured quartsJobFactory in an own one.
        quartzJobFactory(QuartzMonitorJobFactory) {
            if (manager?.hasGrailsPlugin("hibernate") || manager?.hasGrailsPlugin("hibernate4")) {
                sessionFactory = ref("sessionFactory")
            }
            pluginManager = ref("pluginManager")
        }
        **/
    }
}
