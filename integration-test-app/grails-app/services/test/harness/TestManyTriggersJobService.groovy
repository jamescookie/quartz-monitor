package test.harness

import com.agileorbit.schwartz.SchwartzJob
import groovy.transform.CompileStatic
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

@CompileStatic
class TestManyTriggersJobService implements SchwartzJob {

    void buildTriggers() {
        triggers << factory('simpleTrigger').startDelay(10000).intervalInMillis(30000).repeatCount(10).build()
        triggers << factory('cronTrigger').startDelay(10000).cronSchedule('0/6 * 15 * * ?').build()
    }

    void execute(JobExecutionContext context) throws JobExecutionException {
        println "running TestManyTriggersJob"
        Thread.sleep((Math.random() * 10).intValue() * 5129)
    }
}
