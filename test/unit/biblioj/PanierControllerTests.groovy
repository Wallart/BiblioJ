package biblioj



import org.junit.*
import grails.test.mixin.*

@TestFor(PanierController)
@Mock(Panier)
class PanierControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/panier/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.panierInstanceList.size() == 0
        assert model.panierInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.panierInstance != null
    }

    void testSave() {
        controller.save()

        assert model.panierInstance != null
        assert view == '/panier/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/panier/show/1'
        assert controller.flash.message != null
        assert Panier.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/panier/list'

        populateValidParams(params)
        def panier = new Panier(params)

        assert panier.save() != null

        params.id = panier.id

        def model = controller.show()

        assert model.panierInstance == panier
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/panier/list'

        populateValidParams(params)
        def panier = new Panier(params)

        assert panier.save() != null

        params.id = panier.id

        def model = controller.edit()

        assert model.panierInstance == panier
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/panier/list'

        response.reset()

        populateValidParams(params)
        def panier = new Panier(params)

        assert panier.save() != null

        // test invalid parameters in update
        params.id = panier.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/panier/edit"
        assert model.panierInstance != null

        panier.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/panier/show/$panier.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        panier.clearErrors()

        populateValidParams(params)
        params.id = panier.id
        params.version = -1
        controller.update()

        assert view == "/panier/edit"
        assert model.panierInstance != null
        assert model.panierInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/panier/list'

        response.reset()

        populateValidParams(params)
        def panier = new Panier(params)

        assert panier.save() != null
        assert Panier.count() == 1

        params.id = panier.id

        controller.delete()

        assert Panier.count() == 0
        assert Panier.get(panier.id) == null
        assert response.redirectedUrl == '/panier/list'
    }
}
