<%@ page import="biblioj.Livre" %>



<div class="fieldcontain ${hasErrors(bean: livreInstance, field: 'auteur', 'error')} ">
	<label for="auteur">
		<g:message code="livre.auteur.label" default="Auteur" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: livreInstance, field: 'nombreExemplaires', 'error')} required">
	<label for="nombreExemplaires">
		<g:message code="livre.nombreExemplaires.label" default="Nombre Exemplaires" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nombreExemplaires" type="number" value="${livreInstance.nombreExemplaires}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: livreInstance, field: 'nombreExemplairesDisponibles', 'error')} required">
	<label for="nombreExemplairesDisponibles">
		<g:message code="livre.nombreExemplairesDisponibles.label" default="Nombre Exemplaires Disponibles" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nombreExemplairesDisponibles" type="number" value="${livreInstance.nombreExemplairesDisponibles}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: livreInstance, field: 'reservation', 'error')} ">
	<label for="reservation">
		<g:message code="livre.reservation.label" default="Reservation" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: livreInstance, field: 'titre', 'error')} ">
	<label for="titre">
		<g:message code="livre.titre.label" default="Titre" />
		
	</label>
	<g:textField name="titre" value="${livreInstance?.titre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: livreInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="livre.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${biblioj.TypeDocument.list()}" optionKey="id" required="" value="${livreInstance?.type?.id}" class="many-to-one"/>
</div>

