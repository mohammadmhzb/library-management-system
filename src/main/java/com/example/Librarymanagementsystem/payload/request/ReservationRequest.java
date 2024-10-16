package com.example.Librarymanagementsystem.payload.request;

import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;

@Data
public class ReservationRequest {
    @NotNull(message = "Book ID cannot be null")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long bookId;
}
