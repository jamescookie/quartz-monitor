package org.grails.plugins.quartz.monitor

import grails.test.mixin.TestFor
import org.quartz.JobKey
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(QuartzMonitorService)
class QuartzMonitorServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test updateJobStartDate"() {
        given:
        def date = new Date()
        def jobKey = JobKey.jobKey("test1")


        when: "Date is stored"
        service.updateJobStartDate(jobKey, date)

        then:
        service.getAdditionalJobDetails(jobKey) != null
        service.getAdditionalJobDetails(jobKey)."${QuartzMonitorService.START_DATE}" == date
    }

    void "test updateJobEndDate"() {
        given:
        def date = new Date()
        def jobKey = JobKey.jobKey("test2")


        when: "EndDate is stored"
        service.updateJobEndDate(jobKey, date)

        then:
        service.getAdditionalJobDetails(jobKey) != null
        service.getAdditionalJobDetails(jobKey)."${QuartzMonitorService.END_DATE}" == date
    }

    void "test updateJobStartDate NULL"() {
        given:
        def date = null
        def jobKey = JobKey.jobKey("test3")


        when: "StartDate null is stored"
        service.updateJobStartDate(jobKey, date)

        then:
        service.getAdditionalJobDetails(jobKey) != null
        service.getAdditionalJobDetails(jobKey)."${QuartzMonitorService.START_DATE}" == date
    }

    void "test updateJobEndDate NULL"() {
        given:
        def date = null
        def jobKey = JobKey.jobKey("test4")


        when: "EndDate null is stored"
        service.updateJobEndDate(jobKey, date)

        then:
        service.getAdditionalJobDetails(jobKey) != null
        service.getAdditionalJobDetails(jobKey)."${QuartzMonitorService.END_DATE}" == date
    }
}
