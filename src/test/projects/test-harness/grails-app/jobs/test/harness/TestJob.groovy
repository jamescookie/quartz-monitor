package test.harness


class TestJob {
    def description = ''

    static triggers = {
        simple repeatInterval: 50000
    }

    def execute() {
        println "running TestJob"
    }
}
