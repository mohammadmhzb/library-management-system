package com.example.Librarymanagementsystem.service.impl;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.exception.DuplicateEntryException;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.service.IBookService;
import com.example.Librarymanagementsystem.payload.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public Response<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        log.info("Retrieved {} books from the repository.", books.size());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                books
        );
    }

    public Response<BookResponseDTO> getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->{
                    log.warn("Book with ID {} not found", id);
                    return new ResourceNotFoundException("Book not found with ID: " + id);
                });

        log.info("Successfully retrieved book with ID {}: {}", id, book.getTitle());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                BookMapper.toDTO(book)
        );
    }

    @Override
    public Response<List<BookResponseDTO>> getAvailableBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponseDTO> availableBooks = books.stream()
                .filter(book -> book.getAvailability().equals(BookAvailability.AVAILABLE))
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());

        log.info("Retrieved {} available books from the repository.", availableBooks.size());

        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                availableBooks);
    }

    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }


}