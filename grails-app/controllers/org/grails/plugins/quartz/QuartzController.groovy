package org.grails.plugins.quartz

import org.quartz.Scheduler
import org.quartz.Trigger

import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals

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
            quartzScheduler.getJobKeys(jobGroupEquals(jobGroup))?.each {jobKey ->
                def jobName = jobKey.name
                List<Trigger> triggers = quartzScheduler.getTriggersOfJob(jobKey)
                if (triggers) {
                    triggers.each {trigger ->
                        def currentJob = createJob(jobGroup, jobName, jobsList, trigger.key.name)
                        currentJob.trigger = trigger
                        def state = quartzScheduler.getTriggerState(trigger.key)
                        currentJob.triggerStatus = TriggerState.find {
                            it.value() == state
                        } ?: "UNKNOWN"
                    }
                } else {
                    createJob(jobGroup, jobName, jobsList)
                }
            }
        }
        [jobs: jobsList, now: new Date(), scheduler: quartzScheduler]
    }

    private def createJob(String jobGroup, String jobName, ArrayList jobsList, triggerName = "") {
        def currentJob = [:]
        currentJob.group = jobGroup
        currentJob.name = jobName
        def map = QuartzMonitorJobFactory.jobRuns.get(triggerName)
        if (map) currentJob << map
        jobsList.add(currentJob)
        return currentJob
    }

//    def stop = {
//        triggers.put(params.jobName, quartzScheduler.getTrigger(params.triggerName, params.triggerGroup))
//        quartzScheduler.unscheduleJob(params.triggerName, params.triggerGroup)
//        redirect(action: "list")
//    }
//
//    def start = {
//        def trigger = triggers.get(params.jobName)
//        quartzScheduler.scheduleJob(trigger)
//        redirect(action: "list")
//    }
//
//    def pause = {
//        quartzScheduler.pauseJob(params.jobName, params.jobGroup)
//        redirect(action: "list")
//    }
//
//    def resume = {
//        quartzScheduler.resumeJob(params.jobName, params.jobGroup)
//        redirect(action: "list")
//    }
//
//    def runNow = {
//        quartzScheduler.triggerJob(params.jobName, params.jobGroup, null)
//        redirect(action: "list")
//    }

    def startScheduler = {
        quartzScheduler.start()
        redirect(action: "list")
    }

    def stopScheduler = {
        quartzScheduler.standby()
        redirect(action: "list")
    }

}
