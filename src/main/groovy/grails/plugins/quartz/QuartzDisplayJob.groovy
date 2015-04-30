package grails.plugins.quartz

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

/**
 * Invokes execute() on the GrailsTaskClassJob instance whilst recording the time.
 */
class QuartzDisplayJob implements Job {
    GrailsJobFactory.GrailsJob job
    Map<String, Object> jobDetails
    private sessionFactory
    private pluginManager

    QuartzDisplayJob(GrailsJobFactory.GrailsJob job, Map<String, Object> jobDetails, sessionFactory, pluginManager) {
        this.job = job
        this.jobDetails = jobDetails
        this.sessionFactory = sessionFactory
        this.pluginManager = pluginManager
    }

    void execute(final JobExecutionContext context) throws JobExecutionException {
        jobDetails.clear()
        jobDetails.lastRun = new Date()
        jobDetails.status = "running"
        long start = System.currentTimeMillis()
        try {
            job.execute(context)
            flushSession(job.job)
            jobDetails.status = "complete"
        } catch (Throwable e) {
            jobDetails.error = e.class.simpleName + ' : ' + e.message
            jobDetails.status = "error"
            if (e instanceof JobExecutionException) {
                throw e
            }
            throw new JobExecutionException(e.message, e)
        } finally {
            jobDetails.duration = System.currentTimeMillis() - start
        }
    }

    private void flushSession(job) {
        if (job.metaClass.hasProperty(job, 'sessionRequired') && !job.sessionRequired) {
            return
        }

        if (sessionFactory && pluginManager) {
            if (pluginManager.getGrailsPlugin('hibernate4')) {
                sessionFactory.currentSession?.flush()
            } else { // must be hibernate 3 - too much of an assumption??
                org.springframework.orm.hibernate3.SessionFactoryUtils.getSession(sessionFactory, false)?.flush()
            }
        }
    }
}
