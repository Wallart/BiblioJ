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
                    <div id="title">
                        <g:link controller="livre" action="list">
                           BiblioJ
                        </g:link>
                    </div>
                    <div id="search-wrapper">
                        <g:form controller="livre" method="post" action="search">
                            <div id="input-wrapper">
                                <input type="text" name="query" value="${params.query}" placeholder="Rechercher sur BiblioJ" />
                                <input type="submit" value="Rechercher" />
                            </div>
                            <div id="options-wrapper">
                                <div>
                                    <label for="filterTitle">Titre</label>
                                    <input type="checkbox" name="filterTitle" id="filterTitle" ${(params.titleChecked == "true") ? "checked" : ""}>
                                </div>
                                <div>
                                    <label for="filterAuthor">Auteur</label>
                                    <input type="checkbox" name="filterAuthor" id="filterAuthor" ${(params.authorChecked == "true") ? "checked" : ""}>
                                </div>
                                <div>
                                    <label for="filterDoctype">Type</label>
                                    <input type="checkbox" name="filterDoctype" id="filterDoctype" ${(params.doctypeChecked == "true") ? "checked" : ""}>
                                </div>
                            </div>
                        </g:form>
                    </div>
                    <div id="panier-wrapper">
                        <g:form controller="reservation" method="post" action="list">
                            <input type="hidden" name="controleur" value="livre" />
                            <input type="hidden" name="offset" value="${params.get("offset")}"/>
                            <input type="hidden" name="max" value="${params.get("max")}"/>
                            <input type="submit" value="Valider réservation"/>
                        </g:form>
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
