package com.example.Librarymanagementsystem.controller;


import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import com.example.Librarymanagementsystem.payload.response.ApiResponseSchema;
import com.example.Librarymanagementsystem.service.impl.BookService;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
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

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_USER')")
@Tag(name = "USER API", description = "endpoints that only user can access them")
public class UserController {

    private final BookService bookService;
    private final ReservationService reservationService;

    public UserController(BookService bookService, ReservationService reservationService) {
        this.bookService = bookService;
        this.reservationService = reservationService;
    }

    @GetMapping("/books")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    @GetMapping("/reservations/{userId}")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @PostMapping("/reservations")
    @Operation(summary = "Create a new reservation", description = "Add a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new reservation"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Reservation> createReservation(@RequestBody @Validated ReservationRequest reservationRequest)
            throws Exception {
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
    public ResponseEntity<ApiResponseSchema> deleteReservation(@Parameter(description = "ID of the reservation to be deleted") @PathVariable String reservationId) {
        reservationService.deleteReservation(Long.valueOf(reservationId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
