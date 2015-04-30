package test.harness

class NoTriggersJob {
    def group = "MyGroup"

    static triggers = {
    }

    def execute() {
        println "running NoTriggersJob"
    }
}
