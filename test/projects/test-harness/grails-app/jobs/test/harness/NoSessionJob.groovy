package test.harness

class NoSessionJob {
    def timeout = 45000l // execute job once in 45 seconds
    def startDelay = 20000
    def sessionRequired = false
    def group = "MyGroup"

    def execute() {
        println "running NoSessionJob"
    }
}
