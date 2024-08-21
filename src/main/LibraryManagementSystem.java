package main;

import enums.BookGenre;
import model.*;
import repository.BookRepository;
import repository.ReservationRepository;
import test.TestObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    public static final BookRepository bookRepository = new BookRepository();
    public static final ReservationRepository reservationRepository = new ReservationRepository();
    private static User currentUser;
    public static final List<User> users = new ArrayList<>();
    private static final AuthManager authManager = new AuthManager(users, scanner);

    public static void main(String[] args) {
        new TestObjects();
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    authManager.Login();
                    break;
                case 2:
                    authManager.register();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void setCurrentUser(User user){
        currentUser = user;
    }

    public static void systemAdminMenu() {
        SystemAdmin admin = (SystemAdmin) currentUser;
        while (true) {
            System.out.println("1. View All Books");
            System.out.println("2. Add Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    admin.viewAllBooks(bookRepository.getAllBooks());
                    break;
                case 2:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Book ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter Book pages: ");
                    int pages = scanner.nextInt();
                    System.out.print("Enter Book language: ");
                    String language = scanner.nextLine();
                    System.out.print("Enter Book genre: ");
                    BookGenre genre = BookGenre.valueOf(scanner.nextLine());
                    Book newBook = new Book(title, author, pages, isbn, genre, language);
                    admin.addBook(bookRepository.getAllBooks(), newBook);
                    break;
                case 3:
                    System.out.print("Enter Book ISBN to Remove: ");
                    String ISBN = scanner.nextLine();
                    Book bookToRemove = bookRepository.getBookByISBN(ISBN);
                    if (bookToRemove != null) {
                        admin.removeBook(bookRepository.getAllBooks(), bookToRemove);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    public static void reservationManagerMenu() {
        ReservationManager manager = (ReservationManager) currentUser;
        while (true) {
            System.out.println("1. View Reservation Requests");
            System.out.println("2. Approve Reservation");
            System.out.println("3. Reject Reservation");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manager.viewReservationRequests(reservationRepository.findAllReservations());
                    break;
                case 2:
                    System.out.print("Enter Reservation ID to Approve: ");
                    int reservationIdToApprove = scanner.nextInt();
                    Reservation reservationToApprove = reservationRepository.findReservationById(reservationIdToApprove);
                    if (reservationToApprove != null) {
                        manager.approveReservation(reservationToApprove);
                    } else {
                        System.out.println("Reservation not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Reservation ID to Reject: ");
                    int reservationIdToReject = scanner.nextInt();
                    Reservation reservationToReject = reservationRepository.findReservationById(reservationIdToReject);
                    if (reservationToReject != null) {
                        manager.rejectReservation(reservationToReject);
                    } else {
                        System.out.println("Reservation not found.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
          System.out.println();
        }
    }

    public static void regularUserMenu() {
        RegularUser user = (RegularUser) currentUser;
        while (true) {
            System.out.println("1. View Bookable Books");
            System.out.println("2. Request Book Reservation");
            System.out.println("3. View Requested Books");
            System.out.println("4. Delete Reservation Request");
            System.out.println("5. View Reserved Books");
            System.out.println("6. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    user.viewBookableBooks(bookRepository.getAllBooks());
                    break;
                case 2:
                    System.out.print("Enter Book ISBN to Reserve: ");
                    String isbn = scanner.nextLine();
                    Book bookToReserve = bookRepository.getBookByISBN(isbn);
                    if (bookToReserve != null) {
                        user.requestBookReservation(reservationRepository, bookToReserve);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 3:
                    user.viewRequestedBooks();
                    break;
                case 4:
                    System.out.print("Enter Reservation ID to Delete: ");
                    int reservationIdToDelete = scanner.nextInt();
                    Reservation reservationToDelete = reservationRepository.findReservationById(reservationIdToDelete);
                    if (reservationToDelete != null) {
                        user.deleteReservationRequest(reservationToDelete);
                    } else {
                        System.out.println("Reservation not found.");
                    }
                    break;
                case 5:
                    user.viewReservedBooks();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
}
