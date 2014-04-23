package test.harness

class TestFailingJob {
    static triggers = {
        simple startDelay: 30000, repeatInterval: 300000
    }

    def execute() {
        println "running TestFailingJob: ${Domain2.count()}"
        if (Domain2.count() > 0) {
            Domain2.list()*.delete()
        }
    }
}
