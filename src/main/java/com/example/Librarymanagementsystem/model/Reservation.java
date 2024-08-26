package com.example.Librarymanagementsystem.model;

import com.example.Librarymanagementsystem.model.Book;
import com.example.Librarymanagementsystem.model.User;
import com.example.Librarymanagementsystem.model.enums.ReservationStatus;


public class Reservation {
    private static int nextId = 1;
    private final int reservationId;
    private final Book book;
    private final User user;
    private ReservationStatus status;

    public int getReservationId() {
        return reservationId;
    }

    public Reservation(User user, Book book) {
        this.reservationId = nextId++;
        this.book = book;
        this.user = user;
        this.status = ReservationStatus.PENDING;
    }


    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                " book=" + book +
                ", user=" + user +
                ", status=" + status +
                '}';
    }
}

