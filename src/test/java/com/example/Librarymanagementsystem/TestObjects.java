//package test;
//
//import enums.BookGenre;
//import model.*;
//
//import static main.LibraryManagementSystem.*;
//
//public class TestObjects {
//
//    public TestObjects() {
//
//        Book book1 = new Book("Effective Java", "Joshua Bloch", 416, "978-0134685991", BookGenre.NON_FICTION, "English");
//        Book book2 = new Book("Clean Code", "Robert C. Martin", 464, "978-0132350884", BookGenre.NON_FICTION, "English");
//        Book book3 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 180, "978-0743273565", BookGenre.FICTION, "English");
//        Book book4 = new Book("To Kill a Mockingbird", "Harper Lee", 336, "978-0061120084", BookGenre.FICTION, "English");
//        Book book5 = new Book("A Brief History of Time", "Stephen Hawking", 256, "978-0553380163", BookGenre.NON_FICTION, "English");
//        Book book6 = new Book("The Catcher in the Rye", "J.D. Salinger", 214, "978-0316769488", BookGenre.FICTION, "English");
//
//        SystemAdmin systemAdmin = new SystemAdmin("Ali", "Alizadeh", "Ali123", "Ali@123");
//        ReservationManager reservationManager = new ReservationManager("Sara", "Rezaei", "Sara123", "Sara@123");
//        RegularUser regularUser = new RegularUser("Mehdi", "Mozafari", "Mehdi123", "Mehdi@123");
//
//        bookRepository.addBook(book1);
//        bookRepository.addBook(book2);
//        bookRepository.addBook(book3);
//        bookRepository.addBook(book4);
//        bookRepository.addBook(book5);
//        bookRepository.addBook(book6);
//
//        users.add(systemAdmin);
//        users.add(regularUser);
//        users.add(reservationManager);
//
//        reservationRepository.addReservation(new Reservation(regularUser, book2));
//        reservationRepository.addReservation(new Reservation(regularUser, book5));
//    }
//}
