package biblioj

class Reservation {

    String code
    Date dateReservation

    static hasMany = [livre:Livre]

    static constraints = {
    }

    static transactional = true

    static mapping = {
        livre fetch: "join"
    }
}
