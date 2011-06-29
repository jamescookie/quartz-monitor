import test.harness.Domain2
import test.harness.Domain1

class BootStrap {

    def init = { servletContext ->
        Domain1 d1 = new Domain1()
        Domain2 d2 = new Domain2()
        d1.ref = d2
        d1.save()
        d2.save()
    }
    def destroy = {
    }
}
