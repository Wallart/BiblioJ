package biblioj

import org.springframework.dao.DataIntegrityViolationException

class LivreController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        println("List: ${params}")
        params.max = Math.min(max ?: 5, 100)
        [livreInstanceList: Livre.list(params), livreInstanceTotal: Livre.count()]
    }

    def create() {
        [livreInstance: new Livre(params)]
    }

    def save() {
        def livreInstance = new Livre(params)
        if (!livreInstance.save(flush: true)) {
            render(view: "create", model: [livreInstance: livreInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'livre.label', default: 'Livre'), livreInstance.id])
        redirect(action: "show", id: livreInstance.id)
    }

    def show(Long id) {
        def livreInstance = Livre.get(id)
        if (!livreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'livre.label', default: 'Livre'), id])
            redirect(action: "list")
            return
        }

        [livreInstance: livreInstance]
    }

    def edit(Long id) {
        def livreInstance = Livre.get(id)
        if (!livreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'livre.label', default: 'Livre'), id])
            redirect(action: "list")
            return
        }

        [livreInstance: livreInstance]
    }

    def update(Long id, Long version) {
        def livreInstance = Livre.get(id)
        if (!livreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'livre.label', default: 'Livre'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (livreInstance.version > version) {
                livreInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'livre.label', default: 'Livre')] as Object[],
                          "Another user has updated this Livre while you were editing")
                render(view: "edit", model: [livreInstance: livreInstance])
                return
            }
        }

        livreInstance.properties = params

        if (!livreInstance.save(flush: true)) {
            render(view: "edit", model: [livreInstance: livreInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'livre.label', default: 'Livre'), livreInstance.id])
        redirect(action: "show", id: livreInstance.id)
    }

    def delete(Long id) {
        def livreInstance = Livre.get(id)
        if (!livreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'livre.label', default: 'Livre'), id])
            redirect(action: "list")
            return
        }

        try {
            livreInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'livre.label', default: 'Livre'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'livre.label', default: 'Livre'), id])
            redirect(action: "show", id: id)
        }
    }

    def addToPanier() {
        //Recuperer le panier de la session
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
        redirect(action: "list", params: [offset: params.get("offset") , max: params.get("max")])
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
        redirect(action: "list", params: [offset: params.get("offset") , max: params.get("max")])
    }

    def addToReservation() {
        Panier panier = session.getAttribute("panier")
        if (panier) {
            Reservation reserv = new Reservation(code: 1,  dateReservation: new Date()).save(failOnError: true)
            def liste = panier.livre?.asList()*.titre
            while(!liste?.isEmpty()) {
                def livre = Livre.findByTitre(liste.get(0))
                def nbExemplairesDisponible = livre.getNombreExemplairesDisponibles()
                Livre.findByTitre(liste.get(0)).setNombreExemplairesDisponibles(nbExemplairesDisponible - 1)
                reserv.addToLivre(Livre.findByTitre(liste.get(0)))
                liste.remove(0);
            }
            panier.livre?.clear()
        } else {
            // Rien faire car il n'ya rien dans le panier
        }
        // Rediriger vers la vue reservation
        redirect(action: "list", params: [offset: params.get("offset") , max: params.get("max")])
    }
}
