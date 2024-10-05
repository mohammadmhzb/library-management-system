package com.example.Librarymanagementsystem.service.impl;


import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.data.repository.ReservationRepository;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ReservationService implements IReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Transactional
    public Reservation saveReservation(Reservation reservation) throws Exception {
        // Optional: Validate that the user and book exist

        User user = userRepository.findById(reservation.getUser().getId())
                .orElseThrow(() -> new Exception("User not found with id " + reservation.getUser().getId()));

        Book book = bookRepository.findById(reservation.getBook().getId())
                .orElseThrow(() -> new Exception("Book not found with id " + reservation.getBook().getId()));

        reservation.setUser(user);
        reservation.setBook(book);

        // Save the reservation
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservationStatus(Long id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(status);
        reservation.setUpdatedAt(Instant.now());
        if(status.equals(ReservationStatus.APPROVED))
            reservation.getBook().setAvailability(BookAvailability.RESERVED);
        return reservationRepository.save(reservation);
    }
}
