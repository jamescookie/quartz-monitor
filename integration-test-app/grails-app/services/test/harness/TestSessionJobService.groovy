package test.harness

import com.agileorbit.schwartz.SchwartzJob
import grails.transaction.Transactional
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

class TestSessionJobService implements SchwartzJob {

    boolean getSessionRequired() { true }
    String getDescription(){ 'Creates new domain objects' }

    void buildTriggers() {
        triggers << factory().startDelay(35000).intervalInMillis(350000).build()
    }

    @Transactional
    void execute(JobExecutionContext context) throws JobExecutionException {
        println "running TestSessionJob: ${Domain2.count()}"
        new Domain1(ref: new Domain2()).save()
    }
}
