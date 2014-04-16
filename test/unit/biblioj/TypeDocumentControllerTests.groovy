package biblioj


import org.junit.*
import grails.test.mixin.*

@TestFor(TypeDocumentController)
@Mock(TypeDocument)
class TypeDocumentControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/typeDocument/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.typeDocumentInstanceList.size() == 0
        assert model.typeDocumentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.typeDocumentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.typeDocumentInstance != null
        assert view == '/typeDocument/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/typeDocument/show/1'
        assert controller.flash.message != null
        assert TypeDocument.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/typeDocument/list'

        populateValidParams(params)
        def typeDocument = new TypeDocument(params)

        assert typeDocument.save() != null

        params.id = typeDocument.id

        def model = controller.show()

        assert model.typeDocumentInstance == typeDocument
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/typeDocument/list'

        populateValidParams(params)
        def typeDocument = new TypeDocument(params)

        assert typeDocument.save() != null

        params.id = typeDocument.id

        def model = controller.edit()

        assert model.typeDocumentInstance == typeDocument
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/typeDocument/list'

        response.reset()

        populateValidParams(params)
        def typeDocument = new TypeDocument(params)

        assert typeDocument.save() != null

        // test invalid parameters in update
        params.id = typeDocument.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/typeDocument/edit"
        assert model.typeDocumentInstance != null

        typeDocument.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/typeDocument/show/$typeDocument.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        typeDocument.clearErrors()

        populateValidParams(params)
        params.id = typeDocument.id
        params.version = -1
        controller.update()

        assert view == "/typeDocument/edit"
        assert model.typeDocumentInstance != null
        assert model.typeDocumentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/typeDocument/list'

        response.reset()

        populateValidParams(params)
        def typeDocument = new TypeDocument(params)

        assert typeDocument.save() != null
        assert TypeDocument.count() == 1

        params.id = typeDocument.id

        controller.delete()

        assert TypeDocument.count() == 0
        assert TypeDocument.get(typeDocument.id) == null
        assert response.redirectedUrl == '/typeDocument/list'
    }
}
