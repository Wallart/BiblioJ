package biblioj

import org.springframework.dao.DataIntegrityViolationException

class LivreController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def searchableService

    def search() {
        def query = params.query
        params.titleChecked = (params.filterTitle == 'on') ? "true" : "false"
        params.authorChecked = (params.filterAuthor == 'on') ? "true" : "false"
        params.doctypeChecked = (params.filterDoctype == 'on') ? "true" : "false"
        println "${params.titleChecked} , ${params.authorChecked}, ${params.doctypeChecked}"
        if(query) {

            def criteria = Livre.createCriteria()
            def results = criteria {
                or {
                    if (params.titleChecked.equals("true")) {
                        ilike("titre", "%" + query + "%")
                    }

                    if (params.authorChecked.equals("true")) {
                        auteurs {
                            or {
                                ilike("nom", "%" + query + "%")
                                ilike("prenom", "%" + query + "%")
                            }
                        }
                    }

                    if (params.doctypeChecked.equals("true")) {
                        type {
                            ilike("intitule", "%" + query + "%")
                        }
                    }
                }
            }
            if (!results) {
                println "renew"
                results = new ArrayList();
            }
            //def max = Math.min(results.size(), 5)
            session["actualRequest"] = results
            session["titleChecked"] = params.titleChecked
            session["authorChecked"] = params.authorChecked
            session["doctypeChecked"] = params.doctypeChecked
            session["query"] = params.query
        } else {
            session["actualRequest"] = null
        }
        redirect(action: "list", params: params)
    }

    def index(Integer max) {
        redirect(action: "list", params: params)
    }

    def list(Integer max, Integer offset) {
        if((session["actualRequest"]) || (session["actualRequest"]?.isEmpty())) {
            if (session["actualRequest"]?.isEmpty()) {
                [livreInstance: new ArrayList<Livre>(), livreInstanceTotal: 0]
            } else {
                def results = session["actualRequest"]

                def listeComplete = Livre.list()
                def listeNouvelle = new ArrayList<Livre>()
                def listeNomsLivresRecherches = results*.titre
                def listeNomsLivres = listeComplete*.titre

                for (int i = 0; i < listeNomsLivres.size(); i++) {

                    if (listeNomsLivresRecherches.contains(listeNomsLivres.get(i))) {
                        listeNouvelle.add(listeComplete.get(i))
                    }
                }
                if (!offset) {
                    offset = 0
                }
                max = ((offset + 5) < listeNouvelle.size()) ? offset + 5 : listeNouvelle.size() - 1
                [livreInstanceList: listeNouvelle.subList(offset, max), livreInstanceTotal: listeNouvelle.size()]
            }

        } else {
            params.max = Math.min(max ?: 10, 100)
            [livreInstanceList: Livre.list(params), livreInstanceTotal: Livre.count()]
        }
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
}