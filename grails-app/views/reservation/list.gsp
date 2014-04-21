<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BiblioJ</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'main.css')}"/>
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'content.css')}"/>
</head>
<body>
<div id="body-wrapper">
    <header>
        <div id="header-content-wrapper">
            <div id="title">
                <g:link controller="reservation" action="list">
                    Reservation
                </g:link>
            </div>
            <div id="search-wrapper">

            </div>
        </div>
        <div id="panier-wrapper">
            <div id="panier-infos">
                <div id="panier-img"></div>
                <div id="panier-text">
                    <%
                        def panier = session.getAttribute("panier")
                        if(panier?.livre && panier.livre.size() > 0){
                    %>
                    ${panier.livre.size()}
                    <%
                        } else {
                    %>
                    0
                    <%
                        }
                    %>
                    élément(s)
                </div>
            </div>
            <%
                if(panier?.livre && panier.livre.size() > 0){
            %>
            <div id="panier-thumb">
                <h1>Contenu du panier</h1>
                <div class="panier-sep"></div>
                <ul>
                    <g:each in="${panier.livre}" status="i" var="livreInstance">
                        <li>
                            <div class="book-title">${livreInstance.titre}</div>
                            <div class="book-author">Auteur</div>
                            <g:form controller="panier" method="post" action="removeFromPanier">
                                <input type="hidden" name="controleur" value="reservation" />
                                <input type="hidden" name="nomlivre" value="${fieldValue(bean: livreInstance, field: "titre")}" />
                                <input type="submit" class="book-cancel" value="" />
                            </g:form>
                        </li>
                    </g:each>
                </ul>
                <div id="reserve-wrapper">
                    <div class="panier-sep"></div>
                    <g:form controller="reservation" method="post" action="list">
                        <input type="hidden" name="controleur" value="livre" />
                        <input type="hidden" name="offset" value="${params.get("offset")}"/>
                        <input type="hidden" name="max" value="${params.get("max")}"/>
                        <input type="submit" value="Valider réservation"/>
                    </g:form>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </header>
    <div id="content_reservation" style="padding-left: 15px; word-spacing: 1px; letter-spacing: 1px">
        <g:if test="${!session.getAttribute("panier")?.livre?.isEmpty()}">
            <g:form controller="reservation" action="addToReservation">
                <table>
                    <tr>
                        <g:if test="${params.get("dateError") && params.get("vide")?.equals("true")}">
                            <p class="erreurReserv">  ${params.get("dateError")} </p>
                        </g:if>
                        <g:elseif test="${params.get("dateError")}">
                            <p class="erreurReserv"> ${params.get("dateError")} </p>
                        </g:elseif>
                    </tr>
                    <tr>
                        <td>
                            <p class="textReserv" >  Date de reservation : </p>
                        </td>
                        <td>
                            <input class="textReserv" type="date" name="dateDeReservation"/>
                        </td>
                        <g:if test="${!params.get("vide")?.equals("true")}">
                        <td>
                            <input type="submit" value="Valider Définitivement" class="valReserv">
                        </td>
                        </g:if>
                        <td>
                            <input type="submit" value="Annuler" class="valReserv">
                        </td>
                    </tr>

                </table>
            </g:form>
        </g:if>
        <g:elseif test="${(params.get("idReservation")) && (params.get("idReservation")?.toString() != "") }">
            <p class="textReserv" >  Reservation Numero: <i>'${params.get("idReservation")}' Effectuée avec succès </i><br>
            </p>
            <p class="textReserv" >  Limite de récupération: <i>'${params.get("dateReservation")}'</i> + 24h
            </p>
        </g:elseif>
        <g:else>
            <p class="textReserv" >  <i>Panier Vide Impossible de reserver, veuillez ajouter au moins un element</i></p>
        </g:else>
        <g:form controller="livre" action="list" >
            <input type="submit" value="Retour" class="valReserv">
        </g:form>
    </div>
    <footer></footer>
</div>
</body>
</html>