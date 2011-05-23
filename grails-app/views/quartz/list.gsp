<%@ page import="org.grails.plugins.quartz.TriggerState" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Quartz Jobs</title>
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
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Last Run</th>
                            <th>Next Run</th>
                            <th>Status</th>
                            <th>Stop Job</th>
                            <th>Pause/Resume</th>
                            <th>Run now</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${jobs}" status="i" var="job">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${job.name}</td>
                            <td>${job.trigger?.previousFireTime}</td>
                            <td>${job.trigger?.nextFireTime}</td>
                            <td>${job.status}</td>
                            <td><a href="<g:createLink action="stop" params="[triggerName:job.trigger?.name, triggerGroup:job.trigger?.group]"/>">Stop</a></td>
                            <g:if test="${job.status == TriggerState.PAUSED}">
                                <td><a href="<g:createLink action="resume" params="[jobName:job.name, jobGroup:job.group]"/>">Resume</a></td>
                            </g:if>
                            <g:else>
                                <td><a href="<g:createLink action="pause" params="[jobName:job.name, jobGroup:job.group]"/>">Pause</a></td>
                            </g:else>
                            <td><a href="<g:createLink action="runNow" params="[jobName:job.name, jobGroup:job.group]"/>">Run</a></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
