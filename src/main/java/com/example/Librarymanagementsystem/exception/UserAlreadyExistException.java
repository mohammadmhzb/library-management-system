package com.example.Librarymanagementsystem.exception;

public class UserAlreadyExistException extends RuntimeException {

    // Default constructor
    public UserAlreadyExistException() {
        super("User already exists.");
    }

    // Constructor that accepts a custom message
    public UserAlreadyExistException(String message) {
        super(message);
    }

    // Constructor that accepts a custom message and a cause
    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public UserAlreadyExistException(Throwable cause) {
        super(cause);
    }
}

