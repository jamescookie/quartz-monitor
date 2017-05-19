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

    QuartzDisplayJob(GrailsJobFactory.GrailsJob job, Map<String, Object> jobDetails) {
        this.job = job
        this.jobDetails = jobDetails
    }

    void execute(final JobExecutionContext context) throws JobExecutionException {
        jobDetails.clear()
        def jobJob = job.job
        if (hasProperty(jobJob, 'description') && jobJob.description) {
            jobDetails.name = jobJob.description
        }
        jobDetails.lastRun = new Date()
        jobDetails.status = "running"
        long start = System.currentTimeMillis()
        try {
            job.execute(context)
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

}
