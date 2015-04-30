import grails.plugins.quartz.QuartzMonitorJobFactory

class QuartzMonitorGrailsPlugin {
    def version = "1.1"
    def grailsVersion = "1.3 > *"

    def author = "James Cook"
    def authorEmail = "grails@jamescookie.com"
    def title = "Quartz Monitor Grails Plugin"
    def description = 'One clear and concise page that enables you to administer all your Quartz jobs'

    def documentation = "http://grails.org/plugin/quartz-monitor"

    def license = "APACHE"
    def scm = [ url: "http://github.com/jamescookie/quartz-monitor" ]
    def issueManagement = [ system: "GITHUB", url: "http://github.com/jamescookie/quartz-monitor/issues" ]

    def loadAfter = ['quartz']

    def doWithSpring = {
        quartzJobFactory(QuartzMonitorJobFactory) {
            if (manager?.hasGrailsPlugin("hibernate") || manager?.hasGrailsPlugin("hibernate4")) {
                sessionFactory = ref("sessionFactory")
            }
            pluginManager = ref("pluginManager")
        }
    }
}
