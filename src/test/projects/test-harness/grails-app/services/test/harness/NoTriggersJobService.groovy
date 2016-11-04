package test.harness

import com.agileorbit.schwartz.SchwartzJob
import groovy.transform.CompileStatic
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

@CompileStatic
class NoTriggersJobService implements SchwartzJob {

    String getJobGroup() { 'MyGroup' }

    void buildTriggers() {
    }

    void execute(JobExecutionContext context) throws JobExecutionException {
        println "running NoTriggersJob"
    }
}
