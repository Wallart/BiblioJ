package biblioj
import org.compass.core.engine.SearchEngineQueryParseException

class Livre {

    String titre
    Number nombreExemplaires
    Number nombreExemplairesDisponibles
    TypeDocument type

    static hasMany = [auteur:Auteur, reservation:Reservation]
    static belongsTo = [Auteur, Reservation]
    static searchable = {
        components: true
    }

    static constraints = {
    }

    //def searchableService

    /*def index = {
        if (!params.q?.trim()) {
            return [:]
        }
        try {
            return [searchResult: searchableService.search(params.q, params)]
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
    }*/
}
