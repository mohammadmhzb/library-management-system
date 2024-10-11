package com.example.Librarymanagementsystem.service.impl;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.exception.DuplicateEntryException;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.service.IBookService;
import com.example.Librarymanagementsystem.payload.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Response<String> addBook(BookRequestDTO bookRequestDTO) {

        Book book = BookMapper.toEntity(bookRequestDTO);

        if (bookRepository.findByTitle(book.getTitle()) != null) {
            log.warn("Attempted to add a duplicate book: {}", book);
            throw new DuplicateEntryException("Book already exists with the same title: " + book.getTitle());
        }

        log.info("Saving book: {}", book);
        bookRepository.save(book);

        String message = "Book added successfully: " + book.getTitle();
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                message
        );
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

    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAvailableBooks() {
        return getAllBooks().stream().
                filter(book -> book.getAvailability().
                        equals(BookAvailability.AVAILABLE)).collect(Collectors.toList());
    }
}