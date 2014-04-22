package biblioj

import org.bouncycastle.jce.provider.JDKMessageDigest
import org.springframework.dao.DataIntegrityViolationException
import sun.security.provider.SHA

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import groovy.time.TimeCategory


class ReservationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reservationInstanceList: Reservation.list(params), reservationInstanceTotal: Reservation.count()]
    }

    def create() {
        [reservationInstance: new Reservation(params)]
    }

    def save() {
        def reservationInstance = new Reservation(params)
        if (!reservationInstance.save(flush: true)) {
            render(view: "create", model: [reservationInstance: reservationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reservation.label', default: 'Reservation'), reservationInstance.id])
        redirect(action: "show", id: reservationInstance.id)
    }

    def show(Long id) {
        def reservationInstance = Reservation.get(id)
        if (!reservationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reservation.label', default: 'Reservation'), id])
            redirect(action: "list")
            return
        }

        [reservationInstance: reservationInstance]
    }

    def edit(Long id) {
        def reservationInstance = Reservation.get(id)
        if (!reservationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reservation.label', default: 'Reservation'), id])
            redirect(action: "list")
            return
        }

        [reservationInstance: reservationInstance]
    }

    def update(Long id, Long version) {
        def reservationInstance = Reservation.get(id)
        if (!reservationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reservation.label', default: 'Reservation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reservationInstance.version > version) {
                reservationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reservation.label', default: 'Reservation')] as Object[],
                        "Another user has updated this Reservation while you were editing")
                render(view: "edit", model: [reservationInstance: reservationInstance])
                return
            }
        }

        reservationInstance.properties = params

        if (!reservationInstance.save(flush: true)) {
            render(view: "edit", model: [reservationInstance: reservationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reservation.label', default: 'Reservation'), reservationInstance.id])
        redirect(action: "show", id: reservationInstance.id)
    }

    def delete(Long id) {
        def reservationInstance = Reservation.get(id)
        if (!reservationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reservation.label', default: 'Reservation'), id])
            redirect(action: "list")
            return
        }

        try {
            reservationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reservation.label', default: 'Reservation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reservation.label', default: 'Reservation'), id])
            redirect(action: "show", id: id)
        }
    }

    def addToReservation() {

        Panier panier = session.getAttribute("panier")
        ArrayList<String> LivrePlusDisponibles = new ArrayList()
        def idReservation
        def date
        if (panier) {
            idReservation = "${System.currentTimeMillis().toString()} ${session.id}".encodeAsMD5().toUpperCase()
            try {
                date = new Date().parse("yyyy-MM-dd", params.get("dateDeReservation").toString())
                if (date.before(new Date())) {
                    new Date().parse("","sknglksng")
                }

                Reservation reserv = new Reservation(code: idReservation, dateReservation: date).save(failOnError: true)
                def liste = panier.livre?.asList()*.titre
                println liste
                for (int i = 0; i < liste?.size(); i++) {
                    println 12
                    def livre = Livre.findByTitre(liste.get(i))
                    def nbExemplairesDisponible = livre.getNombreExemplairesDisponibles()
                    if (nbExemplairesDisponible == 0) {
                        println 13
                        LivrePlusDisponibles.add(liste.get(i))
                    }
                }
                if (!LivrePlusDisponibles.isEmpty()) {
                    if (LivrePlusDisponibles.size() == liste.size()) {
                        println 1
                        panier.livre?.clear()
                        def messageErreur = "Plus aucun livre disponible ! Réservation impossible."
                        redirect(action: "list", params: [dateError: messageErreur, vide: "true"])
                    } else {
                        println 2
                        StringBuffer messageErreur = new StringBuffer()
                        for (int i = 0; i < LivrePlusDisponibles.size(); i++) {
                            // A optimiser, trouver un moyen de rechercher un objet d'une liste a partir
                            // d'un attribut
                            def livre
                            // Récupérer la liste des titres des livres
                            def titres = panier.livre.asList()*.titre
                            for (int j = 0; j < titres.size(); j++) {
                                if (titres.get(j).equals(LivrePlusDisponibles.get(i))) {
                                    livre = panier.livre.asList().get(j)
                                    break;
                                }
                            }
                            // on retire le livre du panier
                            panier.removeFromLivre(livre)
                            messageErreur.append("Le livre ${LivrePlusDisponibles.get(i)} a été retiré, car il n'est plus disponible. <br>")
                        }
                        redirect(action: "list", params: [dateError: messageErreur.toString()])
                    }
                } else {
                    println 3
                    while (!liste?.isEmpty()) {
                        def livre = Livre.findByTitre(liste.get(0))
                        def nbExemplairesDisponible = livre.getNombreExemplairesDisponibles()
                        if (nbExemplairesDisponible > 0) {
                            Livre.findByTitre(liste.get(0)).setNombreExemplairesDisponibles(nbExemplairesDisponible - 1)
                            reserv.addToLivre(Livre.findByTitre(liste.get(0)))
                            liste.remove(0);
                        }
                    }
                    panier.livre?.clear()
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
                    String reportDate = df.format(date.next())
                    String dateReservation = df.format(date)
                    redirect(action: "list", params: [idReservation: idReservation, dateLimiteReservation: reportDate, dateReservation: dateReservation])
                }
            } catch (java.text.ParseException e) {
                redirect(action: "list", params: [dateError: "Veuillez Entrer Une Date Valide, supérieure au : '${new Date().toGMTString()}'"])
            }

        } else {
            println 4
            // Rien faire car il n'ya rien dans le panier
            redirect(action: "list", params: [idReservation: idReservation, dateReservation: null])
        }
        // Rediriger vers la vue reservation
    }

}