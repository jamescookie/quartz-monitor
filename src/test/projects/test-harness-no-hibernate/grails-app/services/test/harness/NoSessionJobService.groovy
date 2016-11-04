package test.harness

import com.agileorbit.schwartz.SchwartzJob
import groovy.transform.CompileStatic
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

@CompileStatic
class NoSessionJobService implements SchwartzJob  {

    boolean getSessionRequired() { false }
    String getJobGroup() { 'MyGroup' }

    void buildTriggers() {
        triggers << factory().startDelay(20000).intervalInMillis(45000).build()
    }

    void execute(JobExecutionContext context) throws JobExecutionException {
        println "running NoSessionJob"
    }
}
