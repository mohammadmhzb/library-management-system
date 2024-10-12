package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;

import java.util.List;

public interface IReservationService {
    List<Reservation> getAllReservations();

    List<Reservation> getReservationsByUserId(Long userId);

    Reservation saveReservation(ReservationRequest reservationRequest) throws Exception;

    void deleteReservation(Long id);

    Reservation updateReservationStatus(Long id, ReservationStatus status);

    List<Book> getBooksByReservationStatus(ReservationStatus status);
}
