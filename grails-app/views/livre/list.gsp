<%@ page import="biblioj.Livre" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'livre.label', default: 'Livre')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
        <div id="content">
            <ul id="book-list">
                <g:if test="${livreInstanceTotal > 0}">
                    <g:each in="${livreInstanceList}" status="i" var="livreInstance">
                        <li class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <div class="book-thumbnail">
                                <button>Ajout panier</button>
                            </div>
                            <div class="book-title">${fieldValue(bean: livreInstance, field: "titre")}</div>
                            <div class="book-author">
                                <g:each in="${livreInstance.auteurs}" status="j" var="auteurInstance">
                                    <span>${auteurInstance.invokeMethod("toString", null)}</span>
                                </g:each>
                            </div>
                            <div class="book-type">${fieldValue(bean: livreInstance.type, field: "intitule")}</div>
                            <!--<p><g:link action="show" id="${livreInstance.id}">${fieldValue(bean: livreInstance, field: "nombreExemplaires")}</g:link></p>

                        <p>${fieldValue(bean: livreInstance, field: "nombreExemplairesDisponibles")}</p>


                        <p></p>-->

                        </li>
                    </g:each>
                </g:if>
                <g:else>
                    <li id="no-result">Aucun résultat</li>
                </g:else>
            </ul>
            <div id="book-pagination">
                <g:paginate total="${livreInstanceTotal}" />
            </div>
        </div>
		<!--<a href="#list-livre" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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

					</tr>
				</thead>
				<tbody>
				<g:each in="${livreInstanceList}" status="i" var="livreInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${livreInstance.id}">${fieldValue(bean: livreInstance, field: "nombreExemplaires")}</g:link></td>

						<td>${fieldValue(bean: livreInstance, field: "nombreExemplairesDisponibles")}</td>

						<td>${fieldValue(bean: livreInstance, field: "titre")}</td>

						<td>${fieldValue(bean: livreInstance, field: "type")}</td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${livreInstanceTotal}" />
			</div>
		</div>-->
	</body>
</html>
