package grails.plugins.quartz;

import org.hibernate.SessionFactory;
import org.quartz.spi.TriggerFiredBundle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Job factory which enhances GrailsJobFactory.
 *
 * @author James Cook
 * @since 0.1
 */
public class QuartzMonitorJobFactory extends GrailsJobFactory {
    static final java.util.Map<String, Map<String, Object>> jobRuns = new ConcurrentHashMap<String, Map<String, Object>>();
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        String uniqueTriggerName = bundle.getTrigger().getKey().getName();
        Object job = super.createJobInstance(bundle);
        if (job instanceof GrailsJob) {
            Map<String, Object> map;
            if (jobRuns.containsKey(uniqueTriggerName)) {
                map = jobRuns.get(uniqueTriggerName);
            } else {
                map = new ConcurrentHashMap<String, Object>();
                jobRuns.put(uniqueTriggerName, map);
            }
            job = new QuartzDisplayJob((GrailsJob) job, map, sessionFactory);
        }
        return job;
    }
}
