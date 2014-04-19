import biblioj.Livre
import biblioj.Panier
import biblioj.TypeDocument
import org.grails.plugins.csv.CSVMapReader

class BootStrap {

    def init = { servletContext ->
        new File("dataCorpus.csv").toCsvReader(["charset":"iso-8859-1", "separatorChar":";", "skipLines":1]).eachLine { tokens ->
            new Livre(titre:tokens[3], nombreExemplaires: 0, nombreExemplairesDisponibles: 0, type:new TypeDocument(intitule: tokens[1]).save(failOnError: true)).save(failOnError: true)
        }
        def panier = new Panier().save()
    }
    def destroy = {

    }
}
