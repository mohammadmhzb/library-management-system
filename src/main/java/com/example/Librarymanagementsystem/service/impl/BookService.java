package com.example.Librarymanagementsystem.service.impl;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class BookService implements IBookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Stream<Book> getReservableBooks() {
        return bookRepository.findAll().stream().filter(book -> book.getAvailability().equals(BookAvailability.AVAILABLE));
    }

    public Book addBook(Book book) {
        LOGGER.info(book.toString());
        return bookRepository.save(book);
    }

    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }
}