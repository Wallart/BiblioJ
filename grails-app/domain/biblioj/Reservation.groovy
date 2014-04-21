package biblioj

class Reservation {

    String code
    Date dateReservation

    static hasMany = [livre:Livre]

    static constraints = {
    }
}
