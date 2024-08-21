package model;

import enums.UserRole;

import java.util.List;

public class SystemAdmin extends User {


    public SystemAdmin(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, UserRole.ADMIN);

    }
    public void viewAllBooks(List<Book> books) {
        System.out.println("All Books in the Library:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Method to add a book to the library
    public void addBook(List<Book> books, Book book) {
        books.add(book);
        System.out.println("Book added successfully: " + book);
    }

    // Method to remove a book from the library
    public void removeBook(List<Book> books, Book book) {
        if (books.remove(book)) {
            System.out.println("Book removed successfully: " + book);
        } else {
            System.out.println("Book not found: " + book);
        }
    }

    @Override
    public void performAction() {
        System.out.println("SystemAdmin action performed.");
    }
}
