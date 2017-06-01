package grails.plugins.quartz.monitor.listener

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.plugins.quartz.monitor.QuartzMonitorService
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener
import org.springframework.beans.factory.annotation.Autowired
/**
 * Listener for the Quartz Scheduler
 * User: robertoschwald
 *
 */
@Slf4j
@CompileStatic
class QuartzJobListener implements JobListener {

    @Autowired
    QuartzMonitorService quartzMonitorService

    @Override
    String getName() {
        getClass().simpleName
    }

    @Override
    void jobToBeExecuted(JobExecutionContext context) {
        log.debug "${context.jobDetail.key.name}: fired by trigger: ${context.trigger.key}"
        quartzMonitorService.updateJobStartDate(context.jobDetail.key, new Date())
    }

    @Override
    void jobExecutionVetoed(JobExecutionContext context) {
        log.info "${context.jobDetail.key}:  execution VETOED. Trigger: ${context.trigger.key}"
        quartzMonitorService.updateJobEndDate(context.jobDetail.key, null)
    }

    @Override
    void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if (jobException) {
            log.warn "${context.jobDetail.key}: failed with: ${jobException.message}:", jobException
        } else {
            log.info "${context.jobDetail.key}: finished with: ${context.result}"
        }
        quartzMonitorService.updateJobEndDate(context.jobDetail.key, new Date())
    }
}
