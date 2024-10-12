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
import java.util.Optional;
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
                    return new ResourceNotFoundException("Book with ID " + id + " not found.");
                });

        log.info("Successfully retrieved book with ID {}: {}", id, book.getTitle());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                BookMapper.toDTO(book)
        );
    }

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

    public Response<String> removeBook(Long id) {
        if (!bookRepository.existsById(id)) {
            log.warn("Book with ID {} not found", id);
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }
        bookRepository.deleteById(id);
        log.info("Book deleted successfully with ID: {}", id);

        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Book deleted successfully with ID: " +id
        );
    }

    public Response<String> updateBook(Long id, BookRequestDTO bookRequestDTO) throws ResourceNotFoundException {

        if (!bookRepository.existsById(id)) {
            log.warn("Book with ID {} not found", id);
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }

        Book bookDetails = BookMapper.toEntity(bookRequestDTO);


        Optional<Book> updatedBook = bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setAuthor(bookDetails.getAuthor());
            existingBook.setPages(bookDetails.getPages());
            existingBook.setGenre(bookDetails.getGenre());
            existingBook.setLanguage(bookDetails.getLanguage());
            existingBook.setAvailability(bookDetails.getAvailability());
            return bookRepository.save(existingBook);
        });

        log.info("Book updated successfully: {}", updatedBook.get());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Book updated successfully with ID " + id
        );
    }

    public Response<String> patchBook(Long id, BookRequestDTO bookRequestDTO) {

        if (!bookRepository.existsById(id)) {
            log.warn("Book with ID {} not found", id);
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }

        Book bookDetails = BookMapper.toEntity(bookRequestDTO);


        Optional<Book> updatedBook = bookRepository.findById(id).map(existingBook -> {
            if (bookDetails.getTitle() != null) {
                existingBook.setTitle(bookDetails.getTitle());
            }
            if (bookDetails.getAuthor() != null) {
                existingBook.setAuthor(bookDetails.getAuthor());
            }
            if (bookDetails.getPages() > 0) {
                existingBook.setPages(bookDetails.getPages());
            }
            if (bookDetails.getGenre() != null) {
                existingBook.setGenre(bookDetails.getGenre());
            }
            if (bookDetails.getLanguage() != null) {
                existingBook.setLanguage(bookDetails.getLanguage());
            }
            if (bookDetails.getAvailability() != null) {
                existingBook.setAvailability(bookDetails.getAvailability());
            }
            return bookRepository.save(existingBook);
        });

        log.info("Book patched successfully: {}", updatedBook.get());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Book updated successfully with ID " + id
        );

    }


}