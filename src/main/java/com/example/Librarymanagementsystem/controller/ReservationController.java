package com.example.Librarymanagementsystem.controller;


import com.example.Librarymanagementsystem.model.Reservation;
import com.example.Librarymanagementsystem.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.payload.response.ApiResponse;
import com.example.Librarymanagementsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{userid}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable String userid) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(Long.valueOf(userid));
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Reservation> createReservation(Reservation reservation) {
        Reservation newReservation = reservationService.saveReservation(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ApiResponse> deleteReservation(@PathVariable String reservationId) {
        reservationService.deleteReservation(Long.valueOf(reservationId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable String reservationId, ReservationStatus status) {
        Reservation updatedReservation = reservationService.updateReservationStatus(Long.valueOf(reservationId), status);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }
}
