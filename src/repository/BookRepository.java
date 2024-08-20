package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Book;
import enums.BookGenre;
import java.util.stream.Collectors;


public class BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getISBN(), book);
    }

    public void removeBook(Book book) {
        books.remove(book.getISBN());
    }

    // Find books by ISBN
    public Book getBookByISBN(String isbn) {
        return books.get(isbn);
    }

    // Find books by author
    public List<Book> getBooksByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    // Find books by genre
    public List<Book> getBooksByGenre(BookGenre genre) {
        return books.values().stream()
                .filter(book -> book.getGenre() == genre)
                .collect(Collectors.toList());
    }

    // Find books by language
    public List<Book> getBooksByLanguage(String language) {
        return books.values().stream()
                .filter(book -> book.getLanguage().equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }

    // Get all books in the repository
    public List<Book> getAllBooks() {
        return List.copyOf(books.values());
    }

}
