package com.example.Librarymanagementsystem.controller;


import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.service.impl.BookService;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

    private final BookService bookService;
    private final ReservationService reservationService;

    public UserController(BookService bookService, ReservationService reservationService) {
        this.bookService = bookService;
        this.reservationService = reservationService;
    }

    @GetMapping("/books")
    public List<Book> getAvailableBooks() {
        return bookService.getAllBooks().stream().
                filter(book -> book.getAvailability().
                        equals(BookAvailability.AVAILABLE)).collect(Collectors.toList());
    }

    @GetMapping("/reservations/{userId}")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @PostMapping("/reservations")
    public Reservation requestReservation(@RequestBody Reservation reservation) throws Exception {
        return reservationService.saveReservation(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
