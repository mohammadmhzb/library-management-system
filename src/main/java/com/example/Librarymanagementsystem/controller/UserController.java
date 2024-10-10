package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import com.example.Librarymanagementsystem.payload.response.ApiResponseSchema;
import com.example.Librarymanagementsystem.service.impl.BookService;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
@Slf4j
@RequestMapping("/") // Updated base path for better organization
@PreAuthorize("hasRole('ROLE_USER')")
@Tag(name = "USER API", description = "Endpoints that only users can access")
public class UserController {

    private final BookService bookService;
    private final ReservationService reservationService;

    public UserController(BookService bookService, ReservationService reservationService) {
        this.bookService = bookService;
        this.reservationService = reservationService;
    }

    @GetMapping("/books")
    @Operation(summary = "Get available books", description = "Retrieve a list of all available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> availableBooks = bookService.getAvailableBooks();
        return ResponseEntity.ok(availableBooks);
    }

    @PostMapping("/reservations")
    @Operation(summary = "Create a new reservation", description = "Add a new reservation for a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new reservation"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Reservation> createReservation(
            @RequestBody @Validated ReservationRequest reservationRequest) {
        Reservation newReservation = reservationService.saveReservation(reservationRequest);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @Operation(summary = "Delete a reservation", description = "Remove a reservation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseSchema> deleteReservation(
            @Parameter(description = "ID of the reservation to be deleted", required = true)
            @PathVariable String reservationId) {
        reservationService.deleteReservation(Long.valueOf(reservationId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reservations")
    @Operation(summary = "Get filtered books by reservation status", description = "Retrieve a list of books based on their reservation status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books based on reservation status"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation status provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Book>> getFilteredBooks(
            @Parameter(description = "Type of reservation status (e.g., APPROVED, PENDING)", required = true)
            @RequestParam ReservationStatus type) {
        List<Book> books = reservationService.getBooksByReservationStatus(type);
        return ResponseEntity.ok(books);
    }
}
