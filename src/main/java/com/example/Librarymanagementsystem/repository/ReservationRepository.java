package com.example.Librarymanagementsystem.repository;

import com.example.Librarymanagementsystem.model.Reservation;
import com.example.Librarymanagementsystem.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByBookId(Long bookId);
    List<Reservation> findByStatus(ReservationStatus status);
}