package grails.plugins.schwartzmonitor

import grails.plugins.Plugin
import grails.plugins.schwartz.monitor.listener.QuartzJobListener
import groovy.util.logging.Slf4j
import org.quartz.JobListener

@Slf4j
class SchwartzMonitorGrailsPlugin extends Plugin {
    def grailsVersion = "1.3 > *"

    def author = "Robert Oschwald"
    def authorEmail = "robertoschwald@gmail.com"
    def title = "Schwartz Quartz Monitor Grails Plugin"
    def description = 'Enables you to administer all your Schwartz Quartz jobs'

    def documentation = "http://grails.org/plugin/grails-schwartz-monitor"

    def license = "APACHE"
    def scm = [url: "http://github.com/robertoschwald/grails-schwartz-monitor"]
    def issueManagement = [system: "GITHUB", url: "http://github.com/robertoschwald/grails-schwartz-monitor/issues"]

    def loadAfter = ['schwartz', 'quartz']

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
