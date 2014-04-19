package biblioj

class Livre {

    String titre
    Number nombreExemplaires
    Number nombreExemplairesDisponibles
    TypeDocument type

    static hasMany = [auteurs:Auteur, reservations:Reservation]
    static belongsTo = [Auteur, Reservation]
    static searchable = true

    static constraints = {
        type nullable: true
    }
}
