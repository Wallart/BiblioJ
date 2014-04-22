package biblioj

class Livre {

    String titre
    Number nombreExemplaires
    Number nombreExemplairesDisponibles
    TypeDocument type

    static hasMany = [auteurs:Auteur, reservations:Reservation]
    static belongsTo = [Auteur, Reservation]
    static searchable = true


    static mapping = {
        auteurs fetch: "join"
        reservations fetch: "join"
        type fetch: "join"
    }

    static transactional = true

    static constraints = {
        type nullable: true
    }
}
