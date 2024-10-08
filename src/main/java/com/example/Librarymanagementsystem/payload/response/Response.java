package com.example.Librarymanagementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class Response {

    private LocalDateTime timestamp;
    private int status;
    private String message;

    public Response(LocalDateTime timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }
}
