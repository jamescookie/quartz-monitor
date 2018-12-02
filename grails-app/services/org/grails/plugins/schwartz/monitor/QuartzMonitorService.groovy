package org.grails.plugins.schwartz.monitor

import groovy.transform.CompileStatic
import org.grails.exceptions.ExceptionUtils
import org.quartz.JobKey

import java.util.concurrent.ConcurrentHashMap

/**
 * Service for QuartzMonitor.
 */
@CompileStatic
class QuartzMonitorService {
    static final String START_DATE = "startDate"
    static final String END_DATE = "lastRun"
    static final String ERROR = "error"

    ConcurrentHashMap<JobKey, Map<String, Object>> additionalJobDetails = new ConcurrentHashMap()

    def updateJobStartDate(JobKey key, Date startDate) {
        if (!additionalJobDetails.containsKey(key)) additionalJobDetails.put(key, [:])
        additionalJobDetails.get(key).put(START_DATE, startDate)
    }

    def updateJobEndDate(JobKey key, Date endDate, Exception error = null) {
        if (!additionalJobDetails.containsKey(key)) additionalJobDetails.put(key, [:])
        additionalJobDetails.get(key).put(END_DATE, endDate)
        if (error){
            additionalJobDetails.get(key).put(ERROR, ExceptionUtils.getRootCause(error)?.message)
        }
    }

    Map<String, Object> getAdditionalJobDetails(JobKey key) {
        return additionalJobDetails.get(key)
    }
}
