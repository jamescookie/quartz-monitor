package org.grails.plugins.quartz

import org.quartz.Scheduler

class QuartzController {
    static final Map triggers = [:]

    def jobManagerService
    Scheduler quartzScheduler

    def index = {
        redirect(action: "list")
    }

    def list = {
        def jobsList = []
        def listJobGroups = quartzScheduler.getJobGroupNames()
        listJobGroups?.each {jobGroup ->
            quartzScheduler.getJobNames(jobGroup)?.each {jobName ->
                def triggers = quartzScheduler.getTriggersOfJob(jobName, jobGroup)
                if (triggers) {
                    triggers.each {trigger ->
                        def currentJob = createJob(jobGroup, jobName, jobsList)
                        currentJob.trigger = trigger
                        currentJob.triggerName = trigger.name
                        currentJob.triggerGroup = trigger.group
                        def state = quartzScheduler.getTriggerState(trigger.name, trigger.group)
                        currentJob.triggerStatus = TriggerState.find {
                            it.value() == state
                        } ?: "UNKNOWN"
                    }
                } else {
                    createJob(jobGroup, jobName, jobsList)
                }
            }
        }
        [jobs:jobsList]
    }

    private def createJob(String jobGroup, String jobName, ArrayList jobsList) {
        def currentJob = [:]
        currentJob.group = jobGroup
        currentJob.name = jobName
        def map = QuartzMonitorJobFactory.jobRuns.get(jobName)
        if (map) currentJob << map
        jobsList.add(currentJob)
        return currentJob
    }

    def stop = {
        triggers.put(params.jobName, quartzScheduler.getTrigger(params.triggerName, params.triggerGroup))
        quartzScheduler.unscheduleJob(params.triggerName, params.triggerGroup)
        redirect(action: "list")
    }
    
    def start = {
        def trigger = triggers.get(params.jobName)
        quartzScheduler.scheduleJob(trigger)
        redirect(action: "list")
    }

    def pause = {
        quartzScheduler.pauseJob(params.jobName, params.jobGroup)
        redirect(action: "list")
    }

    def resume = {
        quartzScheduler.resumeJob(params.jobName, params.jobGroup)
        redirect(action: "list")
    }

    def runNow = {
        quartzScheduler.triggerJob(params.jobName, params.jobGroup, null)
        redirect(action: "list")
    }

}
