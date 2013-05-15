package test.harness


class CronJob {
    static triggers = {
        cron startDelay:2000, cronExpression: '0 * * * * ?'
    }

    def execute() {
        println "running CronJob"
    }
}
