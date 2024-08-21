package main;

import model.*;
import repository.BookRepository;
import repository.ReservationRepository;
import enums.UserRole;

import java.util.Scanner;

public class LibraryManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookRepository bookRepository = new BookRepository();
    private static final ReservationRepository reservationRepository = new ReservationRepository();
    private static User currentUser;

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Login as System Admin");
            System.out.println("2. Login as Reservation Manager");
            System.out.println("3. Login as Regular User");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    currentUser = new SystemAdmin("Ali", "Alizadeh", "Ali123", "Ali@123", UserRole.ADMIN);
                    systemAdminMenu();
                    break;
//                case 2:
//                    currentUser = new ReservationManager(2, "Manager", "manager@example.com");
//                    reservationManagerMenu();
//                    break;
                case 3:
                    currentUser = new RegularUser("Mehdi", "Mozafari", "Mehdi123", "Mehdi@123");
                    regularUserMenu();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void systemAdminMenu() {
        SystemAdmin admin = (SystemAdmin) currentUser;
        while (true) {
            System.out.println("1. View All Books");
            System.out.println("2. Add Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    admin.viewAllBooks(bookRepository.getAllBooks());
                    break;
                case 2:
//                    System.out.print("Enter Book Title: ");
//                    String title = scanner.nextLine();
//                    System.out.print("Enter Book Author: ");
//                    String author = scanner.nextLine();
//                    System.out.print("Enter Book ISBN: ");
//                    String isbn = scanner.nextLine();
//                    Book newBook = new Book(title, author, isbn);
//                    admin.addBook(bookRepository.getAllBooks(), newBook);
                    break;
                case 3:
//                    System.out.print("Enter Book ID to Remove: ");
//                    int bookId = scanner.nextInt();
//                    Book bookToRemove = bookRepository.findBookById(bookId);
//                    if (bookToRemove != null) {
//                        admin.removeBook(bookRepository.getAllBooks(), bookToRemove);
//                    } else {
//                        System.out.println("Book not found.");
//                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

//    private static void reservationManagerMenu() {
//        ReservationManager manager = (ReservationManager) currentUser;
//        while (true) {
//            System.out.println("1. View Reservation Requests");
//            System.out.println("2. Approve Reservation");
//            System.out.println("3. Reject Reservation");
//            System.out.println("4. Logout");
//            int choice = scanner.nextInt();
//            scanner.nextLine();  // Consume newline
//
//            switch (choice) {
//                case 1:
//                    manager.viewReservationRequests(reservationRepository);
//                    break;
//                case 2:
//                    System.out.print("Enter Reservation ID to Approve: ");
//                    int reservationIdToApprove = scanner.nextInt();
//                    Reservation reservationToApprove = reservationRepository.findReservationById(reservationIdToApprove);
//                    if (reservationToApprove != null) {
//                        manager.approveReservation(reservationRepository, reservationToApprove);
//                    } else {
//                        System.out.println("Reservation not found.");
//                    }
//                    break;
//                case 3:
//                    System.out.print("Enter Reservation ID to Reject: ");
//                    int reservationIdToReject = scanner.nextInt();
//                    Reservation reservationToReject = reservationRepository.findReservationById(reservationIdToReject);
//                    if (reservationToReject != null) {
//                        manager.rejectReservation(reservationRepository, reservationToReject);
//                    } else {
//                        System.out.println("Reservation not found.");
//                    }
//                    break;
//                case 4:
//                    return;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        }
//    }

    private static void regularUserMenu() {
        RegularUser user = (RegularUser) currentUser;
        while (true) {
            System.out.println("1. View Bookable Books");
            System.out.println("2. Request Book Reservation");
            System.out.println("3. View Requested Books");
            System.out.println("4. Delete Reservation Request");
            System.out.println("5. View Reserved Books");
            System.out.println("6. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    user.viewBookableBooks(bookRepository.getAllBooks());
                    break;
                case 2:
//                    System.out.print("Enter Book ID to Reserve: ");
//                    int bookIdToReserve = scanner.nextInt();
//                    Book bookToReserve = bookRepository.findBookById(bookIdToReserve);
//                    if (bookToReserve != null) {
//                        user.requestBookReservation(reservationRepository, bookToReserve);
//                    } else {
//                        System.out.println("Book not found.");
//                    }
                    break;
                case 3:
                    user.viewRequestedBooks();
                    break;
                case 4:
//                    System.out.print("Enter Reservation ID to Delete: ");
//                    int reservationIdToDelete = scanner.nextInt();
//                    Reservation reservationToDelete = reservationRepository.findReservationById(reservationIdToDelete);
//                    if (reservationToDelete != null) {
//                        user.deleteReservationRequest(reservationRepository, reservationToDelete);
//                    } else {
//                        System.out.println("Reservation not found.");
//                    }
                    break;
                case 5:
                    user.viewReservedBooks();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
