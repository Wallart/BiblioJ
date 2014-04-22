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
                    <div id="title"><g:link controller="livre" action="search" params="[query: '']">BiblioJ</g:link></div>
                    <div id="search-wrapper">
                        <%
                            def titleChecked
                            def authorChecked
                            def doctypeChecked
                            def query

                            if (session["actualRequest"]) {
                                titleChecked = session.getAttribute("titleChecked")
                                authorChecked = session.getAttribute("authorChecked")
                                doctypeChecked = session.getAttribute("doctypeChecked")
                                query = session.getAttribute("query")
                            } else {
                                titleChecked = "true"
                                authorChecked = params.authorChecked
                                doctypeChecked = params.doctypeChecked
                                query = params.query
                            }
                        %>
                        <g:form controller="livre" method="post" action="search">
                            <div id="input-wrapper">
                                <input type="text" name="query" value="${query}" placeholder="Rechercher sur BiblioJ" />
                                <input type="submit" value="Rechercher" />
                            </div>
                            <div id="options-wrapper">

                                <div>
                                    <label for="filterTitle">Titre</label>
                                    <input type="checkbox" name="filterTitle" id="filterTitle" ${(titleChecked == "true") ? "checked" : ""}>
                                </div>
                                <div>
                                    <label for="filterAuthor">Auteur</label>
                                    <input type="checkbox" name="filterAuthor" id="filterAuthor" ${(authorChecked == "true") ? "checked" : ""}>
                                </div>
                                <div>
                                    <label for="filterDoctype">Type</label>
                                    <input type="checkbox" name="filterDoctype" id="filterDoctype" ${(doctypeChecked == "true") ? "checked" : ""}>
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
                                <h1>Contenu du panier</h1>
                                <div class="panier-sep"></div>
                                <ul>
                                    <g:each in="${panier.livre}" status="i" var="livreInstance">
                                        <li>
                                            <div class="book-title">${livreInstance.titre}</div>
                                            <div class="book-author">Auteur</div>
                                            <g:form controller="panier" method="post" action="removeFromPanier">
                                                <input type="hidden" name="controleur" value="livre" />
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
                </div>
            </header>
            <div id="content-wrapper">
                <g:layoutBody/>
            </div>
            <footer>
                <div id="footer-wrapper">
                    <div id="top-foot">
                        <span>L'application BiblioJ vous permet de réserver l'ouvrage de votre choix à la Bibliothèque Nationale de France.</span>
                    </div>
                    <div id="sep-foot"></div>
                    <div id="left-foot">
                        <span>BiblioJ a été développé par <a href="mailto:julien.wallart@phelsuma.fr">Julien Wallart</a> et <a href="mailto:koumad.salim@gmail.com">Salim Koumad</a> dans le cadre d'un projet universitaire
                        et n'est en aucun cas approuvé, commandité ou affilié à la Bibliothèque Nationale de France.</span>
                        <a href="http://www.univ-tlse3.fr/" target="_blank"><img src="${resource(dir: 'images', file: 'tlse3_logo.jpg')}" alt="paul sabatier"/></a>
                    </div>
                    <div id="right-foot">
                        <p>Powered by</p>
                        <div>
                            <a href="http://groovy.codehaus.org/" target="_blank"><img id="groovy_logo" src="${resource(dir: 'images', file: 'groovy.png')}" width="45%" /></a>
                            <a href="http://grails.org/" target="_blank"><img id="grails_logo" src="${resource(dir: 'images', file: 'grails.png')}" width="45%" /></a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    </body>
</html>
