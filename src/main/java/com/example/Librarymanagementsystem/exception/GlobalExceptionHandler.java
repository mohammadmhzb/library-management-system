package com.example.Librarymanagementsystem.exception;

import com.example.Librarymanagementsystem.payload.response.Response;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AuthorizationServiceException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response<String>> handleResourceNotFoundException(ResourceNotFoundException ex){
        Response<String> errorResponse = new Response<>(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Response<String>> handleDataAccessException(DataAccessException ex) {
        Response<String> errorResponse = new Response<>(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleGlobalException(Exception ex){
        Response<String> errorResponse = new Response<>(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
        return  new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<String>> handleAccessDeniedException(Exception ex){
        Response<String> errorResponse = new Response<>(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage());
        return  new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthorizationServiceException.class)
    public ResponseEntity<Response<String>> handleAuthorizationException(Exception ex){
        Response<String> errorResponse = new Response<>(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage());
        return  new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        Response<String> errorResponse = new Response<>(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errorMessages);
        return  new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidReservationStatusException.class)
    public ResponseEntity<String> handleInvalidReservationStatus(InvalidReservationStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<Response<String>> handleDuplicateEntryException(DuplicateEntryException ex) {
        Response<String> errorResponse = new Response<>(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
