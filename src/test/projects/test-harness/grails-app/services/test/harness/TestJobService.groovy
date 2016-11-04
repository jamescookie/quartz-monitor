package test.harness

import com.agileorbit.schwartz.SchwartzJob
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException


class TestJobService implements SchwartzJob {

    String getDescription(){ '' }


    void buildTriggers() {
        triggers << factory().intervalInMillis(50000).build()
    }

    void execute(JobExecutionContext context) throws JobExecutionException {
        println "running TestJob"
    }
}
