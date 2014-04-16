package biblioj

class Livre {

    String titre
    Number nombreExemplaires
    Number nombreExemplairesDisponibles
    TypeDocument type

    static hasMany = [auteur:Auteur, reservation:Reservation]
    static belongsTo = [Auteur, Reservation]

    static constraints = {
    }
}
