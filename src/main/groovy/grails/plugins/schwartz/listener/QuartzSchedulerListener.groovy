package grails.plugins.schwartz.listener

import groovy.util.logging.Slf4j
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.SchedulerException
import org.quartz.SchedulerListener
import org.quartz.Trigger
import org.quartz.TriggerKey

/**
 * Listener for the Quartz Scheduler
 * User: robertoschwald
 *
 */
@Slf4j
class QuartzSchedulerListener implements SchedulerListener {
  @Override
  void jobScheduled(Trigger trigger) {
    log.debug("jobScheduled")
  }

  @Override
  void jobUnscheduled(TriggerKey triggerKey) {
    log.debug("jobUnScheduled")
  }

  @Override
  void triggerFinalized(Trigger trigger) {
    log.debug("triggerFinalized")
  }

  @Override
  void triggerPaused(TriggerKey triggerKey) {
    log.debug("triggerPaused")
  }

  @Override
  void triggersPaused(String triggerGroup) {
    log.debug("triggersPaused")
  }

  @Override
  void triggerResumed(TriggerKey triggerKey) {
    log.debug("triggerResumed")
  }

  @Override
  void triggersResumed(String triggerGroup) {
    log.debug("triggersResumed")
  }

  @Override
  void jobAdded(JobDetail jobDetail) {
    log.debug("jobAdded")
  }

  @Override
  void jobDeleted(JobKey jobKey) {
    log.debug("jobDeleted")
  }

  @Override
  void jobPaused(JobKey jobKey) {
    log.debug("jobPaused")
  }

  @Override
  void jobsPaused(String jobGroup) {
    log.debug("jobsPaused")
  }

  @Override
  void jobResumed(JobKey jobKey) {
    log.debug("jobResumed")
  }

  @Override
  void jobsResumed(String jobGroup) {
    log.debug("jobsResumed")
  }

  @Override
  void schedulerError(String msg, SchedulerException cause) {
    log.debug("schedulerError")
  }

  @Override
  void schedulerInStandbyMode() {
    log.debug("schedulerInStandbyMode")
  }

  @Override
  void schedulerStarted() {
    log.debug("schedulerStarted")
  }

  @Override
  void schedulerStarting() {
    log.debug("schedulerStarting")
  }

  @Override
  void schedulerShutdown() {
    log.debug("schedulerShutdown")
  }

  @Override
  void schedulerShuttingdown() {
    log.debug("schedulerShuttingdown")
  }

  @Override
  void schedulingDataCleared() {
    log.debug("schedulingDataCleared")
  }
}
