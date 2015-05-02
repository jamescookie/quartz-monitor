package test.harness

class TestSessionJob {
    def sessionRequired = true
    def description = 'Creates new domain objects'

    static triggers = {
        simple startDelay: 35000, repeatInterval: 350000
    }

    def execute() {
        println "running TestSessionJob: ${Domain2.count()}"
        new Domain1(ref: new Domain2()).save()
    }
}
