<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BiblioJ</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'main.css')}"/>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'content.css')}"/>
        <g:layoutHead/>
    </head>
    <body>
        <div id="body-wrapper">
            <header>
                <div id="header-content-wrapper">
                    <div id="title"><g:link>BiblioJ</g:link></div>
                    <div id="search-wrapper">
                        <g:form controller="livre" method="post" action="search">
                            <div id="input-wrapper">
                                <input type="text" name="query" value="${params.query}" placeholder="Rechercher sur BiblioJ" />
                                <input type="submit" value="Rechercher" />
                            </div>
                            <div id="options-wrapper">
                                <div>
                                    <label for="filterTitle">Titre</label>
                                    <input type="checkbox" name="filterTitle" id="filterTitle" ${(params.titleChecked != "false") ? "checked" : ""}>
                                </div>
                                <div>
                                    <label for="filterAuthor">Auteur</label>
                                    <input type="checkbox" name="filterAuthor" id="filterAuthor" ${(params.authorChecked != "true") ? "" : "checked"}>
                                </div>
                                <div>
                                    <label for="filterDoctype">Type</label>
                                    <input type="checkbox" name="filterDoctype" id="filterDoctype" ${(params.doctypeChecked != "true") ? "" : "checked"}>
                                </div>
                            </div>
                        </g:form>
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
                                <g:form controller="livre" method="post" action="addToReservation">
                                    <input type="hidden" name="offset" value="${params.get("offset")}"/>
                                    <input type="hidden" name="max" value="${params.get("max")}"/>
                                    <input type="submit" value="Valider réservation"/>
                                </g:form>
                                <ul>
                                    <g:each in="${panier.livre}" status="i" var="livreInstance">
                                        <li>${livreInstance.titre}</li>
                                    </g:each>
                                </ul>
                            </div>
                            <%
                                }
                            %>
                    </div>
                </div>
            </header>
            <div id="content-wrapper">
                <g:layoutBody/>
            </div>
            <footer></footer>
        </div>
    </body>
</html>
