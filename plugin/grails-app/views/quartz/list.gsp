<%@ page import="org.quartz.Trigger" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title><g:message code="schwartzmonitor.title"/></title>
  <asset:javascript src="schwartz-monitor.js"/>
  <asset:stylesheet src="schwartz-monitor.css"/>
  <g:if test="${grailsApplication.config.getProperty("quartz.monitor.showCountdown", Boolean, true)}">
    <asset:javascript src="jquery.countdown.js"/>
    <asset:javascript src="jquery.color.js"/>
    <asset:stylesheet src="jquery.countdown.css"/>
  </g:if>
  <g:if test="${grailsApplication.config.getProperty("quartz.monitor.showTickingClock", Boolean, true)}">
    <asset:javascript src="jquery.clock.js"/>
    <asset:stylesheet src="jquery.clock.css"/>
  </g:if>
</head>

<body>
<g:set var="layoutName" value="${grailsApplication.config.quartz?.monitor?.layout}"/>
<g:applyLayout name="${grailsApplication.config.quartz?.monitor?.layout}">
  <div class="body">
    <h1 id="quartz-title">
      <g:message code="schwartzmonitor.headline"/>
      <g:if test="${schedulerInStandbyMode}">
        <a href="<g:createLink action="startScheduler"/>"><asset:image class="quartz-tooltip" data-tooltip="Start scheduler" src="play-all.png"/></a>
      </g:if>
      <g:else>
        <a href="<g:createLink action="stopScheduler"/>"><asset:image class="quartz-tooltip" data-tooltip="Pause scheduler" src="pause-all.png"/></a>
      </g:else>
    </h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div id="clock" data-time="${now.time}">
      <h3><g:message code="schwartzmonitor.currentTime"/>: ${now}</h3>
    </div>

    <div class="list">
      <table id="quartz-jobs">
        <thead>
        <tr>
          <th><g:message code="schwartzmonitor.job.name"/></th>
          <g:if test="${grailsApplication.config.quartz.monitor.showTriggerNames}">
            <th><g:message code="schwartzmonitor.job.triggerName"/></th>
          </g:if>
          <th><g:message code="schwartzmonitor.job.lastRun"/></th>
          <th class="quartz-to-hide"><g:message code="schwartzmonitor.job.result"/></th>
          <th><g:message code="schwartzmonitor.job.nextRun"/></th>
          <th><g:message code="schwartzmonitor.job.actions"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${jobs}" status="i" var="job">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>${job.name}</td>
            <g:if test="${grailsApplication.config.quartz.monitor.showTriggerNames}">
              <td>${job.trigger?.name}</td>
            </g:if>

            <g:set var="tooltip">${(job.error ? "${message(code:'schwartzmonitor.job.exception')}: " + job.error + ". " : "") + (job.duration >= 0 ? "${message(code:'schwartzmonitor.job.duration')}: " + job.duration + "${message(code:'schwartzmonitor.job.ms')}" : "")}</g:set>
            <td class="quartz-tooltip quartz-status ${job.status ?: "not-run"}" data-tooltip="${tooltip}">${job.lastRun}</td>
            <td class="quartz-to-hide">${tooltip}</td>

            <g:if test="${schedulerInStandbyMode || job.triggerStatus == Trigger.TriggerState.PAUSED}">
              <td class="hasCountdown countdown_amount"><g:message code="schwartzmonitor.job.paused"/></td>
            </g:if>
            <g:else>
              <td class="quartz-countdown" data-next-run="${job.trigger?.nextFireTime?.time ?: ""}">${job.trigger?.nextFireTime}</td>
            </g:else>

            <td class="quartz-actions">

              <g:if test="${job.status != 'running'}">
                <g:if test="${job.trigger}">
                  <a href="<g:createLink action="stop" params="[jobName:job.name, triggerName:job.trigger.name, triggerGroup:job.trigger.group]"/>">
                    <asset:image class="quartz-tooltip" data-tooltip="${message(code:'schwartzmonitor.tooltip.stop')}" src="stop.png"/></a>

                  <g:if test="${job.triggerStatus == Trigger.TriggerState.PAUSED}">
                    <a href="<g:createLink action="resume" params="[jobName:job.name, jobGroup:job.group]"/>">
                      <asset:image class="quartz-tooltip" data-tooltip="${message(code:'schwartzmonitor.tooltip.resume')}" src="resume.png"/></a>
                  </g:if>

                  <g:elseif test="${job.trigger.mayFireAgain()}">
                    <a href="<g:createLink action="pause" params="[jobName:job.name, jobGroup:job.group]"/>">
                      <asset:image class="quartz-tooltip" data-tooltip="${message(code:'schwartzmonitor.tooltip.pause')}" src="pause.png"/></a>
                  </g:elseif>
                </g:if>
                <g:else>
                  <a href="<g:createLink action="start" params="[jobName:job.name, jobGroup:job.group]"/>">
                    <asset:image class="quartz-tooltip" data-tooltip="${message(code:'schwartzmonitor.tooltip.start')}" src="start.png"/></a>
                </g:else>

                <a href="<g:createLink action="runNow" params="[jobName:job.name, jobGroup:job.group]"/>">
                  <asset:image class="quartz-tooltip" data-tooltip="${message(code:'schwartzmonitor.tooltip.run')}" src="run.png"/></a>

                <g:if test="${job.trigger instanceof org.quartz.CronTrigger}">
                  <a href="<g:createLink action="editCronTrigger" params="[triggerName:job.trigger.name, triggerGroup:job.trigger.group]"/>">
                    <asset:image class="quartz-tooltip" data-tooltip="${message(code:'schwartzmonitor.tooltip.reschedule')}" src="reschedule.png"/></a>
                </g:if>
              </g:if>

            </td>
          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
  </div>
</g:applyLayout>
</body>

</html>