package biblioj

import org.springframework.dao.DataIntegrityViolationException

class TypeDocumentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [typeDocumentInstanceList: TypeDocument.list(params), typeDocumentInstanceTotal: TypeDocument.count()]
    }

    def create() {
        [typeDocumentInstance: new TypeDocument(params)]
    }

    def save() {
        def typeDocumentInstance = new TypeDocument(params)
        if (!typeDocumentInstance.save(flush: true)) {
            render(view: "create", model: [typeDocumentInstance: typeDocumentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), typeDocumentInstance.id])
        redirect(action: "show", id: typeDocumentInstance.id)
    }

    def show(Long id) {
        def typeDocumentInstance = TypeDocument.get(id)
        if (!typeDocumentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), id])
            redirect(action: "list")
            return
        }

        [typeDocumentInstance: typeDocumentInstance]
    }

    def edit(Long id) {
        def typeDocumentInstance = TypeDocument.get(id)
        if (!typeDocumentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), id])
            redirect(action: "list")
            return
        }

        [typeDocumentInstance: typeDocumentInstance]
    }

    def update(Long id, Long version) {
        def typeDocumentInstance = TypeDocument.get(id)
        if (!typeDocumentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (typeDocumentInstance.version > version) {
                typeDocumentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'typeDocument.label', default: 'TypeDocument')] as Object[],
                          "Another user has updated this TypeDocument while you were editing")
                render(view: "edit", model: [typeDocumentInstance: typeDocumentInstance])
                return
            }
        }

        typeDocumentInstance.properties = params

        if (!typeDocumentInstance.save(flush: true)) {
            render(view: "edit", model: [typeDocumentInstance: typeDocumentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), typeDocumentInstance.id])
        redirect(action: "show", id: typeDocumentInstance.id)
    }

    def delete(Long id) {
        def typeDocumentInstance = TypeDocument.get(id)
        if (!typeDocumentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), id])
            redirect(action: "list")
            return
        }

        try {
            typeDocumentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'typeDocument.label', default: 'TypeDocument'), id])
            redirect(action: "show", id: id)
        }
    }
}
