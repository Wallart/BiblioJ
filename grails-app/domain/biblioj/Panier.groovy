package biblioj

class Panier {

    String idSession

    static hasMany = [livre:Livre]
    static constraints = {

    }
}