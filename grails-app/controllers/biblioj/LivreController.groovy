package biblioj

import org.springframework.dao.DataIntegrityViolationException

class LivreController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def searchableService

    /*def index = {
        if (!params.q?.trim()) {
            return [:]
        }
        try {
            return [searchResult: searchableService.search(params.q, params)]
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
    }*/

    def search() {
        def query = params.query
        if(query){

            def criteria = Livre.createCriteria()
            def results = criteria {
                ilike("titre", "%"+query+"%")
                or {
                    auteurs {
                        ilike("nom", "%" + query + "%")
                        /*or {
                            ilike("prenom", "%"+query+"%")
                        }*/
                    }
                }
            }

            println results
            //def results = searchableService.search(query)
            //params.max = 5

            render(view: "list", model: [livreInstanceList: results, livreInstanceTotal: results.size()])
            //redirect(action: "list")
        }
        else{
            redirect(action: "list")
        }
    }

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
        def panier = Panier.findByIdSession(session.getId())
        if (!panier) {
            println ("Allocation!")
            panier = new Panier(idSession: session.getId()).save()
        }
        def livre = Livre.findByTitre(params.get("nomlivre"))
        panier.addToLivre(livre)
        println (session.getId() + " : " + panier.livre)
        redirect(action: "list")
    }

    def removeFromPanier() {
        def panier = Panier.findByIdSession(session.getId())
        if (!panier) {
            println ("Allocation!")
            panier = new Panier(idSession: session.getId()).save()
        }
        def livre = Livre.findByTitre(params.get("nomlivre"))
        panier.removeFromLivre(livre)
        println (session.getId() + " : " + panier.livre)
        redirect(action: "list")
    }
}
