package org.grails.plugins.schwartz.monitor

import grails.testing.services.ServiceUnitTest
import org.quartz.JobKey
import spock.lang.Specification

class QuartzMonitorServiceSpec extends Specification implements ServiceUnitTest<QuartzMonitorService> {

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
