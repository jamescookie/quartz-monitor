<%@ page import="org.quartz.Trigger" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:set var="layoutName" value="${grailsApplication.config.quartz?.monitor?.layout}" />
        <meta name="layout" content="${layoutName ?: 'main'}" />
        <title>Quartz Jobs</title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'quartz-monitor.css', plugin: 'quartz-monitor')}"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.countdown.css', plugin: 'quartz-monitor')}"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.clock.css', plugin: 'quartz-monitor')}"/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1 id="quartz-title">
                Quartz Jobs
                <g:if test="${scheduler.isInStandbyMode()}">
                    <a href="<g:createLink action="startScheduler"/>"><img class="quartz-tooltip" data-tooltip="Start scheduler" src="<g:resource dir="images" file="play-all.png" plugin="quartz-monitor"/>"></a>
                </g:if>
                <g:else>
                    <a href="<g:createLink action="stopScheduler"/>"><img class="quartz-tooltip" data-tooltip="Pause scheduler" src="<g:resource dir="images" file="pause-all.png" plugin="quartz-monitor"/>"></a>
                </g:else>
            </h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div id="clock" data-time="${now.time}">
                <h3>Current Time: ${now}</h3>
            </div>
            <div class="list">
                <table id="quartz-jobs">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <g:if test="${grailsApplication.config.quartz.monitor.showTriggerNames}">
                                <th>Trigger Name</th>
                            </g:if>
                            <th>Last Run</th>
                            <th class="quartz-to-hide">Result</th>
                            <th>Next Scheduled Run</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${jobs}" status="i" var="job">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${job.name}</td>
                            <g:if test="${grailsApplication.config.quartz.monitor.showTriggerNames}">
                                <td>${job.trigger?.name}</td>
                            </g:if>
                            <g:set var="tooltip">${job.duration >= 0 ? "Job ran in: " + job.duration + "ms" : (job.error ? "Job threw exception: " + job.error : "")}</g:set>
                            <td class="quartz-tooltip quartz-status ${job.status?:"not-run"}" data-tooltip="${tooltip}">${job.lastRun}</td>
                            <td class="quartz-to-hide">${tooltip}</td>
                            <g:if test="${scheduler.isInStandbyMode() || job.triggerStatus == Trigger.TriggerState.PAUSED}">
                                <td class="hasCountdown countdown_amount">Paused</td>
                            </g:if>
                            <g:else>
                                <td class="quartz-countdown" data-next-run="${job.trigger?.nextFireTime?.time ?: ""}">${job.trigger?.nextFireTime}</td>
                            </g:else>
                            <td class="quartz-actions">
                                <g:if test="${job.status != 'running'}">
                                    <g:if test="${job.trigger}">
                                        <a href="<g:createLink action="stop" params="[jobName:job.name, triggerName:job.trigger.name, triggerGroup:job.trigger.group]"/>"><img class="quartz-tooltip" data-tooltip="Stop job from running again" src="<g:resource dir="images" file="stop.png" plugin="quartz-monitor"/>"></a>
                                        <g:if test="${job.triggerStatus == Trigger.TriggerState.PAUSED}">
                                            <a href="<g:createLink action="resume" params="[jobName:job.name, jobGroup:job.group]"/>"><img class="quartz-tooltip" data-tooltip="Resume job schedule" src="<g:resource dir="images" file="resume.png" plugin="quartz-monitor"/>"></a>
                                        </g:if>
                                        <g:elseif test="${job.trigger.mayFireAgain()}">
                                            <a href="<g:createLink action="pause" params="[jobName:job.name, jobGroup:job.group]"/>"><img class="quartz-tooltip" data-tooltip="Pause job schedule" src="<g:resource dir="images" file="pause.png" plugin="quartz-monitor"/>"></a>
                                        </g:elseif>
                                    </g:if>
                                    <g:else>
                                        <a href="<g:createLink action="start" params="[jobName:job.name, jobGroup:job.group]"/>"><img class="quartz-tooltip" data-tooltip="Start job schedule" src="<g:resource dir="images" file="start.png" plugin="quartz-monitor"/>"></a>
                                    </g:else>
                                    <a href="<g:createLink action="runNow" params="[jobName:job.name, jobGroup:job.group]"/>"><img class="quartz-tooltip" data-tooltip="Run now" src="<g:resource dir="images" file="run.png" plugin="quartz-monitor"/>"></a>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        <g:unless test="${grailsApplication.config.quartz.monitor.showCountdown == false}">
            <g:javascript src="jquery.countdown.js" plugin="quartz-monitor"/>
            <g:javascript src="jquery.color.js" plugin="quartz-monitor"/>
        </g:unless>
        <g:unless test="${grailsApplication.config.quartz.monitor.showTickingClock == false}">
            <g:javascript src="jquery.clock.js" plugin="quartz-monitor"/>
        </g:unless>
        <g:javascript src="quartz-monitor.js" plugin="quartz-monitor"/>
    </body>
</html>
