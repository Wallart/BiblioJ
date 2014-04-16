package biblioj

class Reservation {

    Number code
    Date dateReservation

    static hasMany = [livre:Livre]

    static constraints = {
    }
}
