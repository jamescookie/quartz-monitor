package grails.plugins.quartz

import org.quartz.spi.TriggerFiredBundle

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Enhances GrailsJobFactory.
 *
 * @author James Cook
 * @since 0.1
 */
class QuartzMonitorJobFactory extends GrailsJobFactory {

    static final ConcurrentMap<String, Map<String, Object>> jobRuns = new ConcurrentHashMap<String, Map<String, Object>>()

    def sessionFactory

    @Override
    protected createJobInstance(TriggerFiredBundle bundle) {
        String uniqueTriggerName = bundle.trigger.key.name
        def job = super.createJobInstance(bundle)
        if (!(job instanceof GrailsJobFactory.GrailsJob)) {
            return job
        }

        Map<String, Object> map = jobRuns[uniqueTriggerName]
        if (map == null) {
            jobRuns[uniqueTriggerName] = map = new ConcurrentHashMap<String, Object>()
        }

        return new QuartzDisplayJob((GrailsJobFactory.GrailsJob) job, map, sessionFactory)
    }
}
