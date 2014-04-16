<%@ page import="biblioj.Reservation" %>



<div class="fieldcontain ${hasErrors(bean: reservationInstance, field: 'code', 'error')} required">
	<label for="code">
		<g:message code="reservation.code.label" default="Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="code" type="number" value="${reservationInstance.code}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: reservationInstance, field: 'dateReservation', 'error')} required">
	<label for="dateReservation">
		<g:message code="reservation.dateReservation.label" default="Date Reservation" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateReservation" precision="day"  value="${reservationInstance?.dateReservation}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reservationInstance, field: 'livre', 'error')} ">
	<label for="livre">
		<g:message code="reservation.livre.label" default="Livre" />
		
	</label>
	<g:select name="livre" from="${biblioj.Livre.list()}" multiple="multiple" optionKey="id" size="5" value="${reservationInstance?.livre*.id}" class="many-to-many"/>
</div>

