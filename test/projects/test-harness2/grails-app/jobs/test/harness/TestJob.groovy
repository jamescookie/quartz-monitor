package test.harness


class TestJob {
    static triggers = {
        simple repeatInterval: 50000
    }

    def execute() {
        println "running TestJob"
    }
}
