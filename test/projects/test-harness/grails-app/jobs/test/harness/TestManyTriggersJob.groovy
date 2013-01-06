package test.harness


class TestManyTriggersJob {
    static triggers = {
        simple name:'simpleTrigger', startDelay:10000, repeatInterval: 30000, repeatCount: 10
        cron name:'cronTrigger', startDelay:10000, cronExpression: '0/6 * 15 * * ?'
    }

    def execute() {
        println "running TestManyTriggersJob"
        Thread.sleep((Math.random() * 10).intValue() * 5129)
    }
}
