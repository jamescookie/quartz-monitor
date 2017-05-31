package test.harness

import com.agileorbit.schwartz.SchwartzJob
import groovy.transform.CompileStatic
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

@CompileStatic
class CronJobService implements SchwartzJob {
    void buildTriggers() {
        triggers << factory().startDelay(2000).cronSchedule('0 * * * * ?').build()
    }

    void execute(JobExecutionContext context) throws JobExecutionException {
        println "running CronJob"
    }
}
