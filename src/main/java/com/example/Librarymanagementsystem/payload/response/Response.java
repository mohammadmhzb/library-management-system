package com.example.Librarymanagementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class Response<T> {

    private LocalDateTime timestamp;
    private int status;
    private T message;

    public Response(LocalDateTime timestamp, int status, T message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }
}
