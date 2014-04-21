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
                <g:link controller="livre" action="list">
                    BiblioJ
                </g:link>
            </div>
            <div id="search-wrapper">

            </div>
        </div>
    </header>
    <div id="content-wrapper">
    <g:if test="${!session.getAttribute("panier")?.livre?.isEmpty()}">
        <g:form controller="reservation" action="addToReservation">
            <table>
                <tr>
                    <td>
                        <p> Date de reservation : </p>
                    </td>
                    <td>
                        <input type="date" name="dateDeReservation"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Valider">
                    </td>
                </tr>
                <g:if test="${params.get("dateError")}">
                    <p style="color: #cc0000"> ${params.get("dateError")} </p>
                </g:if>
                <tr>
                    <p> </p>
                </tr>
            </table>
        </g:form>
    </g:if>
    <g:elseif test="${(params.get("idReservation")) && (params.get("idReservation")?.toString() != "") }">
        <p> Reservation Numero: '${params.get("idReservation")}' Effectuée avec succès</p>
        <p> Limite de récupération: '${params.get("dateDeReservation")}' + 24h </p>
        <g:link controller="livre" action="list">
            Retour à la biblio
        </g:link>
    </g:elseif>
    <g:else>
        <p> Panier Vide Impossible de reserver, veuillez ajouter au moins un element</p>
        <g:link controller="livre" action="list">
            Retour à la biblio
        </g:link>
    </g:else>

    </div>
    <footer></footer>
</div>
</body>
</html>
