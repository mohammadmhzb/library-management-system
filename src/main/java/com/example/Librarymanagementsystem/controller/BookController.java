package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.service.impl.BookService;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/books")
@Slf4j
@Tag(name = "Book API", description = "CRUD operations for books")
@Validated
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReservationService reservationService;

    @PostMapping("/")
    @Operation(summary = "Add a new book", description = "Add a new book to the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new book"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> createBook(@Validated @RequestBody BookRequestDTO bookRequestDTO) {
        return new ResponseEntity<>(bookService.addBook(bookRequestDTO), HttpStatus.CREATED);
    }


    @GetMapping("/")
    @Operation(summary = "Get books", description = "Retrieve a list of books in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<?>> getBooks(@Parameter(description = "Type of reservation status (e.g., APPROVED, PENDING)", required = true)
                                                @RequestParam ReservationStatus type) {
        if (type == null)
            return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
        else
            return new ResponseEntity<>(reservationService.getBooksByReservationStatus(type), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get one book by id", description = "Retrieve a book by its unique Id in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<BookResponseDTO>> getBookById(@Parameter(description = "ID of the book to be retrieved") @PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Remove a book from the library by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> deleteBook(@Parameter(description = "ID of the book to be deleted") @PathVariable Long id) {
        return new ResponseEntity<>(bookService.removeBook(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a book (PUT)", description = "Update all fields of an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> updateBook(@Validated @RequestBody BookRequestDTO bookRequestDTO,
                                                       @Parameter(description = "ID of the book to be updated")
                                                       @PathVariable Long id) {
        return new ResponseEntity<>(bookService.updateBook(id, bookRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update specific fields of a book (PATCH)", description = "Update specific fields of an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> patchBook(@Validated @RequestBody BookRequestDTO bookRequestDTO,
                                                      @Parameter(description = "ID of the book to be updated")
                                                      @PathVariable Long id) {
        return new ResponseEntity<>(bookService.patchBook(id, bookRequestDTO), HttpStatus.OK);
    }
}
