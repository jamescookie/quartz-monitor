package grails.plugins.quartz

import org.quartz.spi.TriggerFiredBundle

import java.util.concurrent.ConcurrentHashMap

/**
 * Job factory which enhances GrailsJobFactory.
 *
 * @author James Cook
 * @since 0.1
 */
class QuartzMonitorJobFactory extends GrailsJobFactory {

    static final Map<String, Map<String, Object>> jobRuns = new ConcurrentHashMap<String, Map<String, Object>>()

    def sessionFactory

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        String uniqueTriggerName = bundle.trigger.key.name
        Object job = super.createJobInstance(bundle)
        if (job instanceof GrailsJobFactory.GrailsJob) {
            Map<String, Object> map
            if (jobRuns.containsKey(uniqueTriggerName)) {
                map = jobRuns[uniqueTriggerName]
            } else {
                map = new ConcurrentHashMap<String, Object>()
                jobRuns[uniqueTriggerName] = map
            }
            job = new QuartzDisplayJob((GrailsJobFactory.GrailsJob) job, map, sessionFactory)
        }
        return job
    }
}
