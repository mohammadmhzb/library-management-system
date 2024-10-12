package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.service.impl.BookService;
import com.example.Librarymanagementsystem.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
@Tag(name = "ADMIN API", description = "endpoints that only admin can access them")
@Validated
public class AdminController {

    private final BookService bookService;
    private final UserService userService;

    public AdminController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping("/books")
    @Operation(summary = "Add a new book", description = "Add a new book to the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new book"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> createBook(@Validated @RequestBody BookRequestDTO bookRequestDTO) {
        return new ResponseEntity<>(bookService.addBook(bookRequestDTO), HttpStatus.CREATED);
    }


    @GetMapping("/books")
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<List<Book>>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }


    @GetMapping("/books/{id}")
    @Operation(summary = "Get one book by id", description = "Retrieve a book by its unique Id in the library" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<BookResponseDTO>> getBookById(@Parameter(description = "ID of the book to be retrieved") @PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }


    @DeleteMapping("/books/{id}")
    @Operation(summary = "Delete a book", description = "Remove a book from the library by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> deleteBook(@Parameter(description = "ID of the book to be deleted") @PathVariable Long id) {
        return new ResponseEntity<>(bookService.removeBook(id), HttpStatus.OK);
    }



//    ############ USER

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete a user", description = "Remove a users from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> deleteUser(@Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);

    }








}
