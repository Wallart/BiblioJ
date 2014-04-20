import biblioj.Livre
import biblioj.TypeDocument
import biblioj.Auteur

class BootStrap {

    def init = { servletContext ->
        new File("dataCorpus.csv").toCsvReader(["charset":"iso-8859-1", "separatorChar":";", "skipLines":1]).eachLine { tokens ->
            def (nom, prenom) = tokens[4].tokenize(", ")

            //print(nom)
            //println(prenom)

            def doc = new TypeDocument(intitule: tokens[1]).save(failOnError: true)
            def book = new Livre(titre:tokens[3], nombreExemplaires: 0, nombreExemplairesDisponibles: 0, type:doc).save(failOnError: true)
            def author = new Auteur(nom: nom, prenom:prenom).save(failOnError: true)
            author.addToLivre(book)
        }
    }
    def destroy = {
    }
}
