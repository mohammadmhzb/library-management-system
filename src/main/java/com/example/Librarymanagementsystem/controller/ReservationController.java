package com.example.Librarymanagementsystem.controller;

import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.payload.response.ApiResponseSchema;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations API", description = "CRUD operations for managing reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    @Operation(summary = "Get all reservations", description = "Retrieve a list of all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reservations"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{userid}")
    @Operation(summary = "Get reservations by user ID", description = "Retrieve a list of reservations for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reservations for the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@Parameter(description = "ID of the user to retrieve reservations for") @PathVariable String userid) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(Long.valueOf(userid));
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Create a new reservation", description = "Add a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new reservation"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) throws Exception {
        Reservation newReservation = reservationService.saveReservation(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/{reservationId}")
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

    @PatchMapping("/{reservationId}")
    @Operation(summary = "Update reservation status", description = "Update the status of an existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the reservation status"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Reservation> updateReservationStatus(
            @Parameter(description = "ID of the reservation to be updated") @PathVariable String reservationId,
            @RequestBody ReservationStatus status) {
        Reservation updatedReservation = reservationService.updateReservationStatus(Long.valueOf(reservationId), status);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }
}
