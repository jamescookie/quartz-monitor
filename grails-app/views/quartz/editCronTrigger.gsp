<%@ page import="org.quartz.Trigger" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:set var="layoutName" value="${grailsApplication.config.quartz?.monitor?.layout}" />
        <meta name="layout" content="${layoutName ?: 'main'}" />
        <title>Quartz Jobs - Reschedule</title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'quartz-monitor.css', plugin: 'quartz-monitor')}"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.countdown.css', plugin: 'quartz-monitor')}"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.clock.css', plugin: 'quartz-monitor')}"/>
    </head>
    <body>
        <div class="content">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <h1 id="quartz-title">Reschedule [${trigger.name}]</h1>
            <g:form action="saveCronTrigger">
                <g:hiddenField name="triggerName" value="${trigger.name}"/>
                <g:hiddenField name="triggerGroup" value="${trigger.group}"/>
                <fieldset class="form">
                <div>
                    <label>Cron Expression</label>
                    <span class="required-indicator">*</span>
                    <g:textField name="cronexpression" value="${trigger.getCronExpression()}"/>
                </div>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="save" value="Reschedule"/>
                    <g:actionSubmit action="list" name="cancel" value="Cancel"/>
                </fieldset>
            </g:form>
        </div>
        </div>
    </body>
</html>
