<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><g:message code="schwartzmonitor.editcron.title"/></title>
</head>
<body>
<g:applyLayout name="${grailsApplication.config.quartz?.monitor?.layout ?: 'main'}">
    <div class="content">
        <div class="body">
            <h1 id="quartz-title">
                <g:message code="schwartzmonitor.editcron.title"/>
            </h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <h1 id="quartz-title"><g:message code="schwartzmonitor.editcron.reschedule"/> [${trigger.name}]</h1>
            <g:form action="saveCronTrigger">
                <g:hiddenField name="triggerName" value="${trigger.name}"/>
                <g:hiddenField name="triggerGroup" value="${trigger.group}"/>
                <fieldset class="form">
                    <div>
                        <label><g:message code="schwartzmonitor.editcron.expression"/></label>
                        <span class="required-indicator">*</span>
                        <g:textField name="cronexpression" value="${trigger.cronExpression}"/>
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="save" value="${message(code:'schwartzmonitor.editcron.button.save')}"/>
                    <g:actionSubmit action="list" name="cancel" value="${message(code:'schwartzmonitor.editcron.button.cancel')}"/>
                </fieldset>
            </g:form>
        </div>
    </div>
</g:applyLayout>
</body>
</html>
