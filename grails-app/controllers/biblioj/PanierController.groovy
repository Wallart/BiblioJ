package biblioj

import org.springframework.dao.DataIntegrityViolationException

class PanierController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [panierInstanceList: Panier.list(params), panierInstanceTotal: Panier.count()]
    }

    def create() {
        [panierInstance: new Panier(params)]
    }

    def save() {
        def panierInstance = new Panier(params)
        if (!panierInstance.save(flush: true)) {
            render(view: "create", model: [panierInstance: panierInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'panier.label', default: 'Panier'), panierInstance.id])
        redirect(action: "show", id: panierInstance.id)
    }

    def show(Long id) {
        def panierInstance = Panier.get(id)
        if (!panierInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'panier.label', default: 'Panier'), id])
            redirect(action: "list")
            return
        }

        [panierInstance: panierInstance]
    }

    def edit(Long id) {
        def panierInstance = Panier.get(id)
        if (!panierInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'panier.label', default: 'Panier'), id])
            redirect(action: "list")
            return
        }

        [panierInstance: panierInstance]
    }

    def update(Long id, Long version) {
        def panierInstance = Panier.get(id)
        if (!panierInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'panier.label', default: 'Panier'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (panierInstance.version > version) {
                panierInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'panier.label', default: 'Panier')] as Object[],
                        "Another user has updated this Panier while you were editing")
                render(view: "edit", model: [panierInstance: panierInstance])
                return
            }
        }

        panierInstance.properties = params

        if (!panierInstance.save(flush: true)) {
            render(view: "edit", model: [panierInstance: panierInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'panier.label', default: 'Panier'), panierInstance.id])
        redirect(action: "show", id: panierInstance.id)
    }

    def delete(Long id) {
        def panierInstance = Panier.get(id)
        if (!panierInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'panier.label', default: 'Panier'), id])
            redirect(action: "list")
            return
        }

        try {
            panierInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'panier.label', default: 'Panier'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'panier.label', default: 'Panier'), id])
            redirect(action: "show", id: id)
        }
    }

    def addToPanier() {
        //Recuperer le panier de la session
        println params.get("controller")
        Panier panier = session.getAttribute("panier")

        //si le panier n'existe pas on le cree dans la session
        if (!panier) {
            println ("Allocation!")
            panier = new Panier(idSession: session.getId())
            session["panier"] = panier
        }
        //on recupere le livre correspondant a la requete a partir de la BDD
        def livre = Livre.findByTitre(params.get("nomlivre"))

        //si le panier ne contient pas le livre on l'ajoute
        if((!panier.livre) || (!panier.livre*.titre.contains(livre.titre))) {
            panier.addToLivre(livre)
        }
        println (session.getId() + " : " + panier.livre)
        redirect(controller: params.get("controleur"), action: "list", params: [offset: params.get("offset") , max: params.get("max")])
    }

    def removeFromPanier() {

        Panier panier = session.getAttribute("panier")
        if (!panier) {
            println ("Allocation!")
            panier = new Panier(idSession: session.getId())
            session["panier"] = panier
        }

        // A optimiser, trouver un moyen de rechercher un objet d'une liste a partir
        // d'un attribut
        def livre
        // Récupérer la liste des titres des livres
        def liste = panier.livre.asList()*.titre
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).equals(params.get("nomlivre"))) {
                livre = panier.livre.asList().get(i)
                break;
            }
        }
        // on retire le livre du panier
        panier.removeFromLivre(livre)
        println (session.getId() + " : " + panier.livre)
        redirect(controller: params.get("controleur"), action: "list", params: [offset: params.get("offset") , max: params.get("max")])
    }
}
