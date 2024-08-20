package repository;

import java.util.HashMap;
import java.util.Map;
import model.Book;

public class BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getISBN(), book);
    }

    public void removeBook(Book book) {
        books.remove(book.getISBN());
    }





}
