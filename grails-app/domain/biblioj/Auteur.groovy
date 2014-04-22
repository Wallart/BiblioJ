package biblioj

class Auteur {

    String nom
    String prenom

    static hasMany = [livres:Livre]

    static constraints = {
        nom nullable: true
        prenom nullable: true
    }

    static mapping = {
        livres fetch: "join"
    }

    static transactional = true

    String toString() {
        return prenom+" "+nom
    }
}
