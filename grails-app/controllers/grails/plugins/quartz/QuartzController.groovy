package grails.plugins.quartz

import org.grails.plugins.quartz.monitor.QuartzMonitorService
import org.quartz.*
import org.quartz.impl.matchers.GroupMatcher

import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals

class QuartzController {
    static final Map<String, Trigger> triggers = [:]

    Scheduler quartzScheduler
    QuartzMonitorService quartzMonitorService

    def index = {
        redirect(action: "list")
    }

    def list = {
        def jobsList = []
        def listJobGroups = quartzScheduler.jobGroupNames
        listJobGroups?.each {jobGroup ->
            quartzScheduler.getJobKeys(jobGroupEquals(jobGroup))?.each { JobKey jobKey ->
                def jobName = jobKey.name
                List<Trigger> triggers = quartzScheduler.getTriggersOfJob(jobKey)
                if (triggers) {
                    triggers.each {trigger ->
                        def currentJob = createJobModel(jobGroup, jobKey, jobsList, trigger.key.name)
                        currentJob.trigger = trigger
                        def state = quartzScheduler.getTriggerState(trigger.key)
                        currentJob.triggerStatus = Trigger.TriggerState.find {
                            it == state
                        } ?: "UNKNOWN"
                    }
                } else {
                    createJobModel(jobGroup, jobKey, jobsList)
                }
            }
        }
        [jobs: jobsList, now: new Date(), schedulerInStandbyMode: quartzScheduler.isInStandbyMode()]
    }

    private createJobModel(String jobGroup, JobKey jobKey, List jobsList, String triggerName = "") {
        def currentJob = [group: jobGroup, name: jobKey.name] + (quartzMonitorService.getAdditionalJobDetails(jobKey) ?: [:])
        jobsList << currentJob
        return currentJob
    }

    def stop = {
        def triggerKeys = quartzScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(params.triggerGroup))
        def key = triggerKeys?.find {it.name == params.triggerName}
        if (key) {
            def trigger = quartzScheduler.getTrigger(key)
            if (trigger) {
                triggers[params.jobName] = trigger
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
        def trigger = triggers[params.jobName]
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

    def editCronTrigger = {
        def trigger = quartzScheduler.getTrigger(new TriggerKey(params.triggerName, params.triggerGroup))
        if (!(trigger instanceof CronTrigger)) {
            flash.message = "This trigger is not a cron trigger"
            redirect(action: "list")
            return
        }
        [trigger: trigger]
    }

    def saveCronTrigger = {
        if (!params.triggerName || !params.triggerGroup) {
            flash.message = "Invalid trigger parameters"
            redirect(action: "list")
            return
        }

        CronTrigger trigger = quartzScheduler.getTrigger(new TriggerKey(params.triggerName, params.triggerGroup)) as CronTrigger
        if (!trigger) {
            flash.message = "No such trigger"
            redirect(action: "list")
            return
        }

        try {
            trigger.cronExpression = params.cronexpression
            quartzScheduler.rescheduleJob(new TriggerKey(params.triggerName, params.triggerGroup), trigger)
        } catch (Exception ex) {
            flash.message = "cron expression (${params.cronexpression}) was not correct: $ex"
            render(view: "editCronTrigger", model: [trigger: trigger])
            return
        }
        redirect(action: "list")
    }
}
