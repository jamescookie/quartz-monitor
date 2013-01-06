package test.harness


class TestJob {
    def timeout = 50000l // execute job once in 50 seconds

    def execute() {
        println "running TestJob"
    }
}
