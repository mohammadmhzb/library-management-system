package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import com.example.Librarymanagementsystem.payload.response.Response;

import java.util.List;

public interface IReservationService {
    Response<List<Reservation>> getAllReservations();

    Response<List<Reservation>> getReservationsByUserId(Long userId);

    Response<Reservation> saveReservation(ReservationRequest reservationRequest) throws Exception;

    Response<String> deleteReservation(Long id);

    Response<Reservation> updateReservationStatus(Long id, ReservationStatus status);

    Response<List<Book>> getBooksByReservationStatus(ReservationStatus status);
}
