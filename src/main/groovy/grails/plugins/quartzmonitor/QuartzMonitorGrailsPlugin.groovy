package grails.plugins.quartzmonitor

import grails.plugins.Plugin
import grails.plugins.quartz.monitor.listener.QuartzJobListener
import groovy.util.logging.Slf4j
import org.quartz.JobListener

@Slf4j
class QuartzMonitorGrailsPlugin extends Plugin {
    def grailsVersion = "1.3 > *"

    def author = "James Cook"
    def authorEmail = "grails@jamescookie.com"
    def title = "Quartz Monitor Grails Plugin"
    def description = 'Enables you to administer all your Quartz jobs'
    def developers = [
      [ name: 'James Cook', email: 'grails@jamescookie.com' ],
      [ name: 'Robert Oschwald', email: 'robertoschwald@gmail.com' ],
    ]
    def documentation = "http://grails.org/plugin/quartz-monitor"

    def license = "APACHE"
    def scm = [url: "http://github.com/jamescookie/quartz-monitor"]
    def issueManagement = [system: "GITHUB", url: "http://github.com/jamescookie/quartz-monitor/issues"]

    def loadAfter = ['quartz', 'schwartz']

    @Override
    Closure doWithSpring() {{ ->
        monitorQuartzJobListener(QuartzJobListener)
    }}

    @Override
    void doWithApplicationContext() {
        log.debug("Registering Job listener")
        applicationContext.getBean('quartzScheduler').getListenerManager().addJobListener(applicationContext.getBean('monitorQuartzJobListener') as JobListener)
    }
}
