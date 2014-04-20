import biblioj.Livre
import biblioj.Panier
import biblioj.TypeDocument
import org.grails.plugins.csv.CSVMapReader

import biblioj.Livre
import biblioj.TypeDocument
import biblioj.Auteur
import org.grails.plugins.csv.CSVMapReader

class BootStrap {

    def init = { servletContext ->
        new File("dataCorpus.csv").toCsvReader(["charset":"iso-8859-1", "separatorChar":";", "skipLines":1]).eachLine { tokens ->
            def (nom, prenom) = tokens[4].tokenize(", ")

            //print(nom)
            //println(prenom)

            def doc = new TypeDocument(intitule: tokens[1]).save(failOnError: true)
            def nbExemplaires = (int) (Math.random()*1000)
            def nbExemplairesDisponibles = (int) (Math.random()*nbExemplaires)
            def book = new Livre(titre:tokens[3], nombreExemplaires: nbExemplaires, nombreExemplairesDisponibles: nbExemplairesDisponibles, type:doc).save(failOnError: true)
            def author = new Auteur(nom: nom?nom:"Anonyme", prenom:prenom?prenom:"").save(failOnError: true)
            author.addToLivre(book)
        }
    }
    def destroy = {
    }
}