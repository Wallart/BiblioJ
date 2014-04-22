<!DOCTYPE html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'livre.label', default: 'Livre')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div id="content">
        <h1 class="title">Réservation</h1>
        <div id="reservation-view">
            <g:if test="${params.get("dateError") && params.get("vide")?.equals("true")}">
                <p class="errorReserv">  ${params.get("dateError")} </p>
            </g:if>
            <g:elseif test="${params.get("dateError")}">
                <p class="errorReserv"> ${params.get("dateError")} </p>
            </g:elseif>
            <g:if test="${!session.getAttribute("panier")?.livre?.isEmpty()}">
                <div id="dateReserv-form">
                    <g:form controller="reservation" action="addToReservation">
                        <div id="dateReserv-wrapper">
                            <label for="dateReserv">Date de reservation : </label>
                            <input id="dateReserv" type="date" name="dateDeReservation"/>
                        </div>
                        <g:if test="${!params.get("vide")?.equals("true")}">
                            <input type="submit" value="Confirmer">
                        </g:if>
                    </g:form>
                    <g:form controller="panier" action="clearPanier">
                        <input id="cancel-reserve" type="submit" value="Annuler">
                    </g:form>
                </div>
                <ul>
                    <g:each in="${session.getAttribute("panier").livre}" status="i" var="livreInstance">
                        <li>${fieldValue(bean: livreInstance, field: "titre")}</li>
                    </g:each>
                </ul>
            </g:if>
            <g:elseif test="${(params.get("idReservation")) && (params.get("idReservation")?.toString() != "") }">
                <p class="textReserv" style="float: left">Réservation numéro : '${params.get("idReservation")}' effectuée avec succès.</p><br><br>
                <p class="textReserv" style="float: left">Date de reservation : '${params.get("dateReservation")}' effectuée avec succès.</p><br><br>
                <p class="textReserv" style="float: left">Limite de récupération : '${params.get("dateLimiteReservation")}'</p><br><br>
            </g:elseif>
            <g:else>
                <p class="errorReserv" >Panier vide. Réservation impossible, veuillez ajouter au moins un élément</p>
            </g:else>
            <p id="home-link">
                <g:link controller="livre" action="list">
                    Retour à l'accueil
                </g:link>
            <p id="home-link">
        </div>
    </div>
</body>
</html>