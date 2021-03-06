<%@ page import="biblioj.Livre; biblioj.Panier" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'livre.label', default: 'Livre')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
        <div id="content">
            <h1 class="title">Livres</h1>
            <ul id="book-list">
                <g:if test="${livreInstanceTotal > 0}">
                    <g:each in="${livreInstanceList}" status="i" var="livreInstance">
                        <li>
                            <div class="book-thumbnail">
                                <div class="book-interaction">
                                    <%
                                        Panier panier = session.getAttribute("panier")
                                        if(panier?.livre && panier.livre*.titre.contains(livreInstance.titre)){
                                    %>
                                    <g:form controller="panier" action="removeFromPanier" method="post" params="[offset: params.get('offset'), max: params.get('max')]">
                                        <input type="hidden" name="controleur" value="livre" />
                                        <input type="hidden" name="nomlivre" value="${fieldValue(bean: livreInstance, field: "titre")}" />
                                        <input class="removeFromPanier" type="submit" value="" />
                                    </g:form>
                                    <%
                                        } else if(livreInstance.nombreExemplairesDisponibles > 0){
                                    %>
                                    <g:form controller="panier" action="addToPanier" method="post" params="[offset: params.get('offset'), max: params.get('max')]">
                                        <input type="hidden" name="controleur" value="livre" />
                                        <input type="hidden" name="nomlivre" value="${fieldValue(bean: livreInstance, field: "titre")}" />
                                        <input class="addToPanier" type="submit" value="" />
                                    </g:form>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                            <div class="book-title">${fieldValue(bean: livreInstance, field: "titre")}</div>
                            <div class="book-author">
                                <g:each in="${livreInstance.auteurs}" status="j" var="auteurInstance">
                                    <span>${auteurInstance.invokeMethod("toString", null)}</span>
                                </g:each>
                            </div>
                            <div class="book-type">${fieldValue(bean: livreInstance.type, field: "intitule")}</div>
                            <div class="book-quantity">Exemplaires diponibles : ${livreInstance.nombreExemplairesDisponibles}</div>
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
	</body>
</html>
