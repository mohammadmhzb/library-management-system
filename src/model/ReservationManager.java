package model;

import enums.ReservationStatus;
import enums.UserRole;
import java.util.List;

public class ReservationManager extends User {

    private List<Reservation> reservationRequests;

    // Constructor
    public ReservationManager(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, UserRole.MANAGER);
    }


    public void viewReservationRequests(List<Reservation> reservationRequests) {
        System.out.println("Reservation Requests:");
        for (Reservation reservation : reservationRequests) {
            System.out.println(reservation);
        }
    }


    public void approveReservation(Reservation reservation) {
        if (reservation.getStatus() == ReservationStatus.PENDING) {
            reservation.setStatus(ReservationStatus.APPROVED);
            System.out.println("Reservation approved: " + reservation);
        } else {
            System.out.println("Reservation cannot be approved. Current status: " + reservation.getStatus());
        }
    }


    public void rejectReservation(Reservation reservation) {
        if (reservation.getStatus() == ReservationStatus.PENDING) {
            reservation.setStatus(ReservationStatus.REJECTED);
            System.out.println("Reservation rejected: " + reservation);
        } else {
            System.out.println("Reservation cannot be rejected. Current status: " + reservation.getStatus());
        }
    }

    @Override
    public void performAction() {
        // Define specific actions for ReservationManager if needed
        System.out.println("ReservationManager action performed.");
    }
}
