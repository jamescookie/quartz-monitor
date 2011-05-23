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
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Last Run</th>
                            <th>Next Run</th>
                            <th>Status</th>
                            <th>Stop Job</th>
                            <th>Pause Job</th>
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
                            <td><a href="<g:createLink action="pause" params="[jobName:job.name, jobGroup:job.group]"/>">Pause</a></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
