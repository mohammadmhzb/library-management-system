package com.example.Librarymanagementsystem.controller;


import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reservation-manager")
@PreAuthorize("hasRole('ROLE_MANAGER')")

public class ReservationManagerController {

    private final ReservationService reservationService;

    public ReservationManagerController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PutMapping("/reservations/{id}")
    public Reservation updateReservationStatus(@PathVariable Long id, @RequestParam String status) {
        return reservationService.updateReservationStatus(id, ReservationStatus.valueOf(status));
    }
}
