import org.grails.plugins.quartz.QuartzMonitorJobFactory

class QuartzMonitorGrailsPlugin {
    def version = "0.2"
    def grailsVersion = "1.1 > *"
    def dependsOn = [quartz:"0.4.2"]
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
