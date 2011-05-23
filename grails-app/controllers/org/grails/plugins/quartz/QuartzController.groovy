package org.grails.plugins.quartz

import org.codehaus.groovy.grails.plugins.quartz.JobObject
import org.quartz.Scheduler

class QuartzController {
    def jobManagerService
    Scheduler quartzScheduler

    def index = {
        redirect(action: "list")
    }

    def list = {
        def jobs = []
        def allJobs = jobManagerService.getAllJobs()
        allJobs.each {JobObject jobObject ->
            def job = [:]
            job.name = jobObject.name
            job.group = jobObject.group
            job.trigger = quartzScheduler.getTrigger(jobObject.triggerName, jobObject.triggerGroup)
            job.status = TriggerState.find {it.value() == jobObject.status} ?: "UNKNOWN"
            jobs << job
        }

        [jobs:jobs]
    }

    def stop = {
        quartzScheduler.unscheduleJob(params.triggerName, params.triggerGroup)
        redirect(action: "list")
    }

    def pause = {
        quartzScheduler.pauseJob(params.jobName, params.jobGroup)
        redirect(action: "list")
    }

}
