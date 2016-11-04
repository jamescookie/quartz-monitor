package grails.plugins.schwartzmonitor

import com.agileorbit.schwartz.QuartzMonitorJobFactory

class SchwartzMonitorGrailsPlugin {
    def grailsVersion = "1.3 > *"

    def author = "Robert Oschwald"
    def authorEmail = "robertoschwald@gmail.com"
    def title = "Schwartz Quartz Monitor Grails Plugin"
    def description = 'Enables you to administer all your Schwartz Quartz jobs'

    def documentation = "http://grails.org/plugin/schwartz-monitor"

    def license = "APACHE"
    def scm = [ url: "http://github.com/robertoschwald/schwartz-monitor" ]
    def issueManagement = [ system: "GITHUB", url: "http://github.com/robertoschwald/schwartz-monitor/issues" ]

    def loadAfter = ['schwartz']

    def doWithSpring = {
        quartzJobFactory(QuartzMonitorJobFactory) {
            if (manager?.hasGrailsPlugin("hibernate") || manager?.hasGrailsPlugin("hibernate4")) {
                sessionFactory = ref("sessionFactory")
            }
            pluginManager = ref("pluginManager")
        }
    }
}
