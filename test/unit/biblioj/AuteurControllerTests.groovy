package biblioj


import org.junit.*
import grails.test.mixin.*

@TestFor(AuteurController)
@Mock(Auteur)
class AuteurControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/auteur/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.auteurInstanceList.size() == 0
        assert model.auteurInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.auteurInstance != null
    }

    void testSave() {
        controller.save()

        assert model.auteurInstance != null
        assert view == '/auteur/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/auteur/show/1'
        assert controller.flash.message != null
        assert Auteur.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/auteur/list'

        populateValidParams(params)
        def auteur = new Auteur(params)

        assert auteur.save() != null

        params.id = auteur.id

        def model = controller.show()

        assert model.auteurInstance == auteur
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/auteur/list'

        populateValidParams(params)
        def auteur = new Auteur(params)

        assert auteur.save() != null

        params.id = auteur.id

        def model = controller.edit()

        assert model.auteurInstance == auteur
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/auteur/list'

        response.reset()

        populateValidParams(params)
        def auteur = new Auteur(params)

        assert auteur.save() != null

        // test invalid parameters in update
        params.id = auteur.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/auteur/edit"
        assert model.auteurInstance != null

        auteur.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/auteur/show/$auteur.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        auteur.clearErrors()

        populateValidParams(params)
        params.id = auteur.id
        params.version = -1
        controller.update()

        assert view == "/auteur/edit"
        assert model.auteurInstance != null
        assert model.auteurInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/auteur/list'

        response.reset()

        populateValidParams(params)
        def auteur = new Auteur(params)

        assert auteur.save() != null
        assert Auteur.count() == 1

        params.id = auteur.id

        controller.delete()

        assert Auteur.count() == 0
        assert Auteur.get(auteur.id) == null
        assert response.redirectedUrl == '/auteur/list'
    }
}
