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
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${jobs}" status="i" var="job">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${job.name}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
