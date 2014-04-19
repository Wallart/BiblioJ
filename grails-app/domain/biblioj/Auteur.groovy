package biblioj

class Auteur {

    String nom
    String prenom

    static hasMany = [livres:Livre]

    static constraints = {
        nom nullable: true
        prenom nullable: true
    }
}
