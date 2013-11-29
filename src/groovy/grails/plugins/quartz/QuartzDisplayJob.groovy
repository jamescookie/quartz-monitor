package grails.plugins.quartz

import org.quartz.Job
import org.hibernate.SessionFactory
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

/**
 * Quartz Job implementation that invokes execute() on the GrailsTaskClassJob instance whilst recording the time
 */
class QuartzDisplayJob implements Job {
    GrailsJobFactory.GrailsJob job
    Map<String, Object> jobDetails
    private SessionFactory sessionFactory

    QuartzDisplayJob(GrailsJobFactory.GrailsJob job, Map<String, Object> jobDetails, SessionFactory sessionFactory) {
        this.job = job
        this.jobDetails = jobDetails
        this.sessionFactory = sessionFactory
    }

    void execute(final JobExecutionContext context) throws JobExecutionException {
        jobDetails.clear()
        jobDetails.lastRun = new Date()
        jobDetails.status = "running"
        long start = System.currentTimeMillis()
        try {
            job.execute(context)
            if (shouldFlushSession(job.job)) {
                org.springframework.orm.hibernate3.SessionFactoryUtils.getSession(sessionFactory, false)?.flush()
            }
        } catch (Throwable e) {
            jobDetails.error = e.message
            jobDetails.status = "error"
            if (e instanceof JobExecutionException) {
                throw e
            }
            throw new JobExecutionException(e.message, e)
        }
        jobDetails.status = "complete"
        jobDetails.duration = System.currentTimeMillis() - start
    }

    private boolean shouldFlushSession(job) {
        boolean shouldFlush = sessionFactory != null
        if (job.metaClass.hasProperty(job, 'sessionRequired') && job.sessionRequired == false) {
            shouldFlush = false
        }
        return shouldFlush
    }
}
