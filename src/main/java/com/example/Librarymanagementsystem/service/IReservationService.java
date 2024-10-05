package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;

import java.util.List;

public interface IReservationService {
    List<Reservation> getAllReservations();

    List<Reservation> getReservationsByUserId(Long userId);

    Reservation saveReservation(Reservation reservation) throws Exception;

    void deleteReservation(Long id);

    Reservation updateReservationStatus(Long id, ReservationStatus status);
}
