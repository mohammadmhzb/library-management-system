package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.payload.response.ApiResponseSchema;
import com.example.Librarymanagementsystem.service.impl.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@Tag(name = "Books API", description = "CRUD operations for books")
@RequestMapping("/api/books")
public class BookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("")
    @Operation(summary = "Add a new book", description = "Add a new book to the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new book"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        LOGGER.info(book.toString());
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }


    @GetMapping("")
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get one book by id", description = "Retrieve a book by its unique Id in the library" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a book"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    public ResponseEntity<Optional<Book>> getBookById(@Parameter(description = "ID of the book to be retrieved") @PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Remove a book from the library by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the book"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseSchema> deleteBook(@Parameter(description = "ID of the book to be deleted") @PathVariable Long id) {
        bookService.removeBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a book (PUT)", description = "Update all fields of an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Book> updateBook(@Parameter(description = "ID of the book to be updated") @PathVariable Long id,
                                           @RequestBody Book bookDetails) {
        return bookService.getBookById(id)
                .map(existingBook -> {
                    existingBook.setTitle(bookDetails.getTitle());
                    existingBook.setAuthor(bookDetails.getAuthor());
                    existingBook.setPages(bookDetails.getPages());
                    existingBook.setGenre(bookDetails.getGenre());
                    existingBook.setLanguage(bookDetails.getLanguage());
                    existingBook.setAvailability(bookDetails.getAvailability());
                    Book updatedBook = bookService.addBook(existingBook);
                    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }


    @PatchMapping("/{id}")
    @Operation(summary = "Update specific fields of a book (PATCH)", description = "Update specific fields of an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Book> patchBook(@Parameter(description = "ID of the book to be updated") @PathVariable Long id,
                                          @RequestBody Book bookDetails) {
        return bookService.getBookById(id)
                .map(existingBook -> {
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
                    Book updatedBook = bookService.addBook(existingBook);
                    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

}
