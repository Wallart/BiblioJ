
<%@ page import="biblioj.Panier; biblioj.Livre" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'livre.label', default: 'Livre')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-livre" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-livre" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="nombreExemplaires" title="${message(code: 'livre.nombreExemplaires.label', default: 'Nombre Exemplaires')}" />
					
						<g:sortableColumn property="nombreExemplairesDisponibles" title="${message(code: 'livre.nombreExemplairesDisponibles.label', default: 'Nombre Exemplaires Disponibles')}" />
					
						<g:sortableColumn property="titre" title="${message(code: 'livre.titre.label', default: 'Titre')}" />

						<th><g:message code="livre.type.label" default="Type" /></th>
                        <th>
                            <g:form method="post" action="addToReservation">
                                <input type="hidden" name="offset" value="${params.get("offset")}"/>
                                <input type="hidden" name="max" value="${params.get("max")}"/>
                                <input type="submit" name="Valider Reservation" value="Reserver"/>
                            </g:form>
                        </th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${livreInstanceList}" status="i" var="livreInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${livreInstance.id}">${fieldValue(bean: livreInstance, field: "nombreExemplaires")}</g:link></td>
					
						<td>${fieldValue(bean: livreInstance, field: "nombreExemplairesDisponibles")}</td>
					
						<td>${fieldValue(bean: livreInstance, field: "titre")}</td>
					
						<td>${fieldValue(bean: livreInstance, field: "type")}</td>

                        <td>
                            <%
                                Panier panier = session.getAttribute("panier")
                                if(livreInstance.nombreExemplairesDisponibles == 0) {
                            %>
                                   <p style="color: '#8b0000'"> Stock Epuisé </p>
                            <%
                                } else if((!panier?.livre) || (!panier.livre*.titre.contains(livreInstance.titre))) {
                            %>
                                <g:form action="addToPanier" method="post" params="[offset: params.get('offset'), max: params.get('max')]">
                                    <input type="hidden" name="nomlivre" value="${fieldValue(bean: livreInstance, field: "titre")}" />
                                    <input type="submit" value="Ajouter Au Panier" />
                                </g:form>
                            <%
                                } else {
                            %>
                            <g:form action="removeFromPanier" method="post" params="[offset: params.get('offset'), max: params.get('max')]">
                                <input type="hidden" name="nomlivre" value="${fieldValue(bean: livreInstance, field: "titre")}" />
                                <input type="submit" value="Retirer Du Panier" />
                            </g:form>
                            <%
                                }
                            %>
                        </td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${livreInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
