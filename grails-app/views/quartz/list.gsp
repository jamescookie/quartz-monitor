<%@ page import="org.grails.plugins.quartz.TriggerState" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Quartz Jobs</title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'quartz-monitor.css', plugin: 'quartz-monitor')}"/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1>Quartz Jobs</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div>
                <h3>Current Time: ${new Date()}</h3>
            </div>
            <div class="list">
                <table id="quartz-jobs">
                    <thead>
                        <tr>
                            <th>Name</th>
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
                            <g:set var="tooltip">${job.duration ? "Job ran in: " + job.duration + "ms" : (job.error ? "Job threw exception: " + job.error : "")}</g:set>
                            <td class="quartz-tooltip quartz-status ${job.status?:"not-run"}" data-tooltip="${tooltip}">${job.lastRun}</td>
                            <td class="quartz-to-hide">${tooltip}</td>
                            <td>${job.trigger?.nextFireTime}</td>
                            <td class="quartz-actions">
                                <g:if test="${job.status != 'running'}">
                                    <g:if test="${job.trigger}">
                                        <a href="<g:createLink action="stop" params="[triggerName:job.trigger.name, triggerGroup:job.trigger.group]"/>"><img class="quartz-tooltip" data-tooltip="Stop job from running again" src="<g:resource dir="images" file="stop.png" plugin="quartz-monitor"/>"></a>
                                        <g:if test="${job.triggerStatus == TriggerState.PAUSED}">
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
        <g:javascript src="quartz-monitor.js" plugin="quartz-monitor"/>
    </body>
</html>
