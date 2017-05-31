package org.grails.plugins.schwartz.monitor

import groovy.transform.CompileStatic
import org.quartz.JobKey

import java.util.concurrent.ConcurrentHashMap

/**
 * Service for QuartzMonitor.
 */
@CompileStatic
class QuartzMonitorService {
    static final String START_DATE = "startDate"
    static final String END_DATE = "lastRun"

    ConcurrentHashMap<JobKey, Map<String, Object>> additionalJobDetails = new ConcurrentHashMap<JobKey, Map>()

    def updateJobStartDate(JobKey key, Date startDate) {
        if (!additionalJobDetails.contains(key)) additionalJobDetails.put(key, [:])
        additionalJobDetails.get(key).put(START_DATE, startDate)
    }

    def updateJobEndDate(JobKey key, Date endDate) {
        if (!additionalJobDetails.contains(key)) additionalJobDetails.put(key, [:])
        additionalJobDetails.get(key).put(END_DATE, endDate)
    }

    Map<String, Object> getAdditionalJobDetails(JobKey key) {
        return additionalJobDetails.get(key)
    }
}
