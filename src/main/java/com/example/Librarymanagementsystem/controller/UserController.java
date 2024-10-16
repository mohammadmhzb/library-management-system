package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.payload.response.UserResponseDTO;
import com.example.Librarymanagementsystem.service.impl.BookService;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
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

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private final UserService userService;
    private final GoogleCalendarController googleCalendarController;


    public UserController(BookService bookService, ReservationService reservationService, UserService userService, GoogleCalendarController googleCalendarController) {
        this.bookService = bookService;
        this.reservationService = reservationService;
        this.userService = userService;
        this.googleCalendarController = googleCalendarController;
    }

    @GetMapping("/books/")
    @Operation(summary = "Get available books", description = "Retrieve a list of all available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<List<BookResponseDTO>>> getAvailableBooks(@Parameter(description = "get only available books or all books")
                                                                                 @RequestParam(required = false, defaultValue = "false") boolean isAvailable) {
        if (isAvailable)
            return new ResponseEntity<>(bookService.getAvailableBooks(), HttpStatus.OK);
        else
            return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    @Operation(summary = "Update a book (PUT)", description = "Update all fields of an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> updateBook(@Validated @RequestBody BookRequestDTO bookRequestDTO,
                                                       @Parameter(description = "ID of the book to be updated")
                                                       @PathVariable Long id){
        return new ResponseEntity<>(bookService.updateBook(id, bookRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("/books/{id}")
    @Operation(summary = "Update specific fields of a book (PATCH)", description = "Update specific fields of an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> patchBook(@Validated @RequestBody BookRequestDTO bookRequestDTO,
                                                      @Parameter(description = "ID of the book to be updated")
                                                      @PathVariable Long id){
        return new ResponseEntity<>(bookService.patchBook(id, bookRequestDTO), HttpStatus.OK);
    }


    @PostMapping("/reservations")
    @Operation(summary = "Create a new reservation", description = "Add a new reservation for a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new reservation"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<Reservation>> createReservation(
            @RequestBody @Validated ReservationRequest reservationRequest) throws IOException {
//        ZonedDateTime now = ZonedDateTime.now();
//
//        ZonedDateTime futureDate = now.plusDays(30);
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//        String formattedStartDateTime = now.format(formatter);
//        String formattedEndDateTime = futureDate.format(formatter);
//
//        EventRequest eventRequest = new EventRequest();
//         eventRequest.setSummary("sss");
//         eventRequest.setDescription("d;l;sad;la");
//         eventRequest.setLocation("Online meeting");
//         eventRequest.setStartDateTime(formattedStartDateTime);
//         eventRequest.setEndDateTime(formattedEndDateTime);
//
//        googleCalendarController.createEvent(eventRequest);
        Response<Reservation> newReservation = reservationService.saveReservation(reservationRequest);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @Operation(summary = "Delete a reservation", description = "Remove a reservation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> deleteReservation(
            @Parameter(description = "ID of the reservation to be deleted", required = true)
            @PathVariable String reservationId) {
        Response<String> message = reservationService.deleteReservation(Long.valueOf(reservationId));
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reservations")
    @Operation(summary = "Get filtered books by reservation status", description = "Retrieve a list of books based on their reservation status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books based on reservation status"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation status provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<List<Book>>> getFilteredBooks(
            @Parameter(description = "Type of reservation status (e.g., APPROVED, PENDING)", required = true)
            @RequestParam ReservationStatus type) {
        Response<List<Book>> books = reservationService.getBooksByReservationStatus(type);
        return ResponseEntity.ok(books);
    }

//    ############ USER

    @GetMapping("users/{id}")
    @Operation(summary = "Get one user by ID", description = "Retrieve a user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<UserResponseDTO>> getUserById(@Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);

    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update a user (PUT)", description = "Update all fields of an existing user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> updateUser(@Validated @RequestBody UserRequestDTO userRequestDTO,
                                                       @Parameter(description = "ID of the user to be updated")
                                                       @PathVariable Long id){
        return new ResponseEntity<>(userService.updateUser(id, userRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    @Operation(summary = "Update specific fields of a user (PATCH)", description = "Update specific fields of an existing user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response<String>> patchUser(@Validated @RequestBody UserRequestDTO userRequestDTO,
                                                      @Parameter(description = "ID of the user to be updated")
                                                      @PathVariable Long id){
        return new ResponseEntity<>(userService.patchUser(id, userRequestDTO), HttpStatus.OK);
    }

}
