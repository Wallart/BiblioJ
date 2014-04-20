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
                    <g:form controller="livre" action="search">
                        <div id="search-wrapper">
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
                        </div>
                    </g:form>
                </div>
            </header>
            <div id="content-wrapper">
                <g:layoutBody/>
            </div>
            <footer></footer>
        </div>
    </body>
</html>
