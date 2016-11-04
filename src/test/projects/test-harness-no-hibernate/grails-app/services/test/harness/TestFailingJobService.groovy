package test.harness

import com.agileorbit.schwartz.SchwartzJob
import grails.transaction.Transactional
import groovy.transform.CompileStatic
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

@CompileStatic
class TestFailingJobService implements SchwartzJob {

    boolean getSessionRequired() { false }

    void buildTriggers() {
        triggers << factory().startDelay(30000).intervalInMillis(300000).build()
    }

    @Transactional
    void execute(JobExecutionContext context) throws JobExecutionException {
        println "running TestFailingJob: ${Domain2.count()}"
        if (Domain2.count() > 0) {
            Domain2.list()*.delete()
        }
    }
}
