package grails.plugins.quartz

import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.impl.matchers.GroupMatcher

import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals

class QuartzController {
    static final Map<String, Trigger> triggers = [:]

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
                        currentJob.triggerStatus = Trigger.TriggerState.find {
                            it == state
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

    def stop = {
        def triggerKeys = quartzScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(params.triggerGroup))
        def key = triggerKeys?.find {it.name == params.triggerName}
        if (key) {
            def trigger = quartzScheduler.getTrigger(key)
            if (trigger) {
                triggers.put(params.jobName, trigger)
                quartzScheduler.unscheduleJob(key)
            } else {
                flash.message = "No trigger could be found for $key"
            }
        } else {
            flash.message = "No trigger key could be found for $params.triggerGroup : $params.triggerName"
        }
        redirect(action: "list")
    }

    def start = {
        def trigger = triggers.get(params.jobName)
        if (trigger) {
            quartzScheduler.scheduleJob(trigger)
        } else {
            flash.message = "No trigger could be found for $params.jobName"
        }
        redirect(action: "list")
    }

    def pause = {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(params.jobGroup))
        def key = jobKeys?.find {it.name == params.jobName}
        if (key) {
            quartzScheduler.pauseJob(key)
        } else {
            flash.message = "No job key could be found for $params.jobGroup : $params.jobName"
        }
        redirect(action: "list")
    }

    def resume = {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(params.jobGroup))
        def key = jobKeys?.find {it.name == params.jobName}
        if (key) {
            quartzScheduler.resumeJob(key)
        } else {
            flash.message = "No job key could be found for $params.jobGroup : $params.jobName"
        }
        redirect(action: "list")
    }

    def runNow = {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(params.jobGroup))
        def key = jobKeys?.find {it.name == params.jobName}
        if (key) {
            quartzScheduler.triggerJob(key)
        } else {
            flash.message = "No job key could be found for $params.jobGroup : $params.jobName"
        }
        redirect(action: "list")
    }

    def startScheduler = {
        quartzScheduler.start()
        redirect(action: "list")
    }

    def stopScheduler = {
        quartzScheduler.standby()
        redirect(action: "list")
    }

}
