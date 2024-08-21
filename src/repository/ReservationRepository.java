package repository;

import enums.ReservationStatus;
import model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }


    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }


    public List<Reservation> findAllReservations() {
        return new ArrayList<>(reservations);
    }


    public Reservation findReservationByStatus(ReservationStatus status) {
        return reservations.stream().filter(reservation -> reservation.getStatus() == status).findFirst().orElse(null);
    }

    public Reservation findReservationById(int id) {
        return reservations.stream().filter(reservation -> reservation.getReservationId() == id).findFirst().orElse(null);
    }
}

