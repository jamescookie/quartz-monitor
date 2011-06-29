package test.harness

class TestFailingJob {
    def timeout = 30000l // execute job once in 30 seconds
    def startDelay = 30000

    def execute() {
        println "running fail job ${Domain2.count()}"
        if (Domain2.count() > 0) {
            Domain2.list()*.delete()
        }
    }
}
