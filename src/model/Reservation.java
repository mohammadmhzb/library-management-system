package model;

import enums.ReservationStatus;

public class Reservation {
    private final Book book;
    private final User user;
    private ReservationStatus status;

    public Reservation(User user, Book book) {
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

