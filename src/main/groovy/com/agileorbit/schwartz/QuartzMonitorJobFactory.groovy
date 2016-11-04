package com.agileorbit.schwartz

import grails.plugins.schwartz.QuartzDisplayJob
import org.quartz.Job
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.spi.TriggerFiredBundle

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Enhanced SchwartzJobFactory, Returns a QuartzDisplayJob instead of a SchwartzJob
 *
 * @author James Cook
 * @author Robert Oschwald
 * @since 0.4
 */
class QuartzMonitorJobFactory extends SchwartzJobFactory {

    static final ConcurrentMap<String, Map<String, Object>> jobRuns = new ConcurrentHashMap<String, Map<String, Object>>()

    def sessionFactory
    def pluginManager

    @Override
    protected Job newInstance(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        String uniqueTriggerName = bundle.trigger.key.name
        def job = super.newInstance(bundle, scheduler)
        if (!(job instanceof SchwartzJob)) {
            return job
        }

        Map<String, Object> map = jobRuns[uniqueTriggerName]
        if (map == null) {
            jobRuns[uniqueTriggerName] = map = new ConcurrentHashMap<String, Object>()
        }

        return new QuartzDisplayJob((SchwartzJob) job, map, sessionFactory, pluginManager)
    }
}
