package biblioj

class Panier {

    String idSession

    static hasMany = [livre:Livre]
    static constraints = {

    }

    static mapping = {
        livre fetch: "join"
    }
}