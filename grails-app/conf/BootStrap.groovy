import biblioj.Livre
import biblioj.TypeDocument
import biblioj.Auteur

class BootStrap {

    def init = { servletContext ->
        /*new File("dataCorpus.csv").toCsvReader(["charset":"iso-8859-1", "separatorChar":";", "skipLines":1]).eachLine { tokens ->
            def (nom, prenom) = tokens[4].tokenize(", ")*/

            /*
             * On vérifie que le Type de Document n'existe pas déjà.
             * On vérifie aussi que son intitulé n'est pas vide.
             */
            /*def doc = TypeDocument.findByIntitule(tokens[1])
            if(doc == null && tokens[1].size() > 0){
                doc = new TypeDocument(intitule: tokens[1]).save(failOnError: true)
            }*/

            /*
             * On vérifie que le Livre n'existe pas déjà.
             */
            /*def book = Livre.findByTitre(tokens[3])
            if(book == null){
                book = new Livre(titre:tokens[3], nombreExemplaires: 0, nombreExemplairesDisponibles: 0, type:doc).save(failOnError: true)
            }*/

            /*
             * On vérifie que l'Auteur n'existe pas déjà.
             */
            /*def author = Auteur.findByNomAndPrenom(nom, prenom)
            if(author == null){
                author = new Auteur(nom: nom, prenom: prenom).save(failOnError: true)
            }
            author.addToLivres(book)
        }*/
    }
    def destroy = {
    }
}
