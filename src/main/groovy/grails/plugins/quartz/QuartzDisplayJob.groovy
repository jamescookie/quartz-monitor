package grails.plugins.quartz

import grails.plugins.GrailsVersionUtils
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
        def jobJob = job.job

        jobDetails.lastRun = new Date()
        jobDetails.status = "running"
        long start = System.currentTimeMillis()
        try {
            job.execute(context)
            flushSession(jobJob)
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

    private boolean hasProperty(thing, name) {
        thing.metaClass.hasProperty(thing, name)
    }

    private void flushSession(job) {
        if (hasProperty(job, 'sessionRequired') && !job.sessionRequired) {
            return
        }

        if (sessionFactory && pluginManager) {
            if (pluginManager.getGrailsPlugin('hibernate4') || GrailsVersionUtils.isVersionGreaterThan("4.0.0", pluginManager.getGrailsPlugin('hibernate').version)) {
                sessionFactory.currentSession?.flush()
            } else { // must be hibernate 3 - too much of an assumption??
                org.springframework.orm.hibernate3.SessionFactoryUtils.getSession(sessionFactory, false)?.flush()
            }
        }
    }
}
