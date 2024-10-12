package com.example.Librarymanagementsystem.service.impl;


import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.data.repository.ReservationRepository;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import com.example.Librarymanagementsystem.security.services.UserDetailsImpl;
import com.example.Librarymanagementsystem.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReservationService implements IReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final BookRepository bookRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Transactional
    public Reservation saveReservation(ReservationRequest reservationRequest) {
        UserDetailsImpl userDetails = getCurrentUser();

        Book book = bookRepository.findById(reservationRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + reservationRequest.getBookId()));

        Reservation reservation = new Reservation();
        User user = createUserFromUserDetails(userDetails);
        reservation.setUser(user);
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
    public List<Book> getBooksByReservationStatus(ReservationStatus status) {
        UserDetailsImpl userDetails = getCurrentUser();
        return getReservationsByUserId(userDetails.getId()).stream()
                .filter(reservation -> reservation.getStatus().equals(status))
                .map(Reservation::getBook)
                .collect(Collectors.toList());
    }

    private UserDetailsImpl getCurrentUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private User createUserFromUserDetails(UserDetailsImpl userDetails) {
        User user = new User();
        user.setId(userDetails.getId());
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return user;
    }

}
