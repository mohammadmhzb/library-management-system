package com.example.Librarymanagementsystem.service.impl;


import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.data.repository.ReservationRepository;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import com.example.Librarymanagementsystem.security.services.UserDetailsImpl;
import com.example.Librarymanagementsystem.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    public Reservation saveReservation(ReservationRequest reservationRequest) throws Exception {
        User userDetails = (User) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        Book book = bookRepository.findById(reservationRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + reservationRequest.getBookId()));
        Reservation reservation = new Reservation();
        reservation.setUser(userDetails);
        reservation.setBook(book);
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

    @Override
    public List<Book> getReservedBooksByUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        Stream<Reservation> approvedReservations = getReservationsByUserId(userDetails.getId()).stream().
                filter(reservation -> reservation.getStatus().equals(ReservationStatus.APPROVED));
        List <Book> reservedBooks = new ArrayList<>();
        approvedReservations.forEach(reservation -> reservedBooks.add(reservation.getBook()));
        return reservedBooks;
    }

    @Override
    public List<Book> getRequestedBooksByUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        Stream<Reservation> pendingReservations = getReservationsByUserId(userDetails.getId()).stream().
                filter(reservation -> reservation.getStatus().equals(ReservationStatus.PENDING));
        List <Book> requestedBooks = new ArrayList<>();
        pendingReservations.forEach(reservation -> requestedBooks.add(reservation.getBook()));
        return requestedBooks;
    }
}
