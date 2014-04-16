<%@ page import="biblioj.TypeDocument" %>



<div class="fieldcontain ${hasErrors(bean: typeDocumentInstance, field: 'intitule', 'error')} ">
	<label for="intitule">
		<g:message code="typeDocument.intitule.label" default="Intitule" />
		
	</label>
	<g:textField name="intitule" value="${typeDocumentInstance?.intitule}"/>
</div>

