package test.harness

class NoSessionJob {
    def sessionRequired = false
    def group = "MyGroup"

    static triggers = {
        simple startDelay: 20000, repeatInterval: 45000
    }

    def execute() {
        println "running NoSessionJob"
    }
}
