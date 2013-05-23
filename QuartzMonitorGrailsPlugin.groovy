import grails.plugins.quartz.QuartzMonitorJobFactory

class QuartzMonitorGrailsPlugin {
    def version = "0.3-RC1"
    def grailsVersion = "1.2 > *"
    def dependsOn = [quartz:"1.0-RC4"]
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "James Cook"
    def authorEmail = "grails@jamescookie.com"
    def title = "Monitor your Quartz jobs"
    def description = '''\\
One clear and concise page that enables you to administer all your Quartz jobs.
'''

    def documentation = "http://grails.org/plugin/quartz-monitor"

    def license = "APACHE"
    def scm = [ url: "http://github.com/jamescookie/quartz-monitor" ]

    def doWithSpring = {
        quartzJobFactory(QuartzMonitorJobFactory) {
            sessionFactory = ref("sessionFactory")
        }
    }
}
