package model;

import enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import static enums.BookAvailability.AVAILABLE;

public class RegularUser extends User {

    private List<Book> requestedBooks;
    private List<Reservation> reservedBooks;

    // Constructor
    public RegularUser(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, UserRole.REGULAR_USER);
        this.requestedBooks = new ArrayList<>();
        this.reservedBooks = new ArrayList<>();
    }


    public void viewBookableBooks(List<Book> books) {
        System.out.println("Books available for reservation:");
        for (Book book : books) {
            if (book.getAvailability() == AVAILABLE) {
                System.out.println(book);
            }
        }
    }


    public void requestBookReservation(Book book) {
        if (book.getAvailability() == AVAILABLE) {
            requestedBooks.add(book);
            System.out.println("Reservation request submitted for book: " + book);
        } else {
            System.out.println("Book is not available for reservation: " + book);
        }
    }


    public void viewRequestedBooks() {
        System.out.println("Requested Books:");
        for (Book book : requestedBooks) {
            System.out.println(book);
        }
    }


    public void deleteReservationRequest(Reservation reservation) {
        if (reservedBooks.remove(reservation)) {
            System.out.println("Reservation request deleted: " + reservation);
        } else {
            System.out.println("Reservation not found: " + reservation);
        }
    }

    public void viewReservedBooks() {
        System.out.println("Reserved Books:");
        for (Reservation reservation : reservedBooks) {
            System.out.println(reservation.getBook());
        }
    }

    @Override
    public void performAction() {
        System.out.println("RegularUser action performed.");
    }
}
