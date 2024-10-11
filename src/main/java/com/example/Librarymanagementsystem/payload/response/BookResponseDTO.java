package com.example.Librarymanagementsystem.payload.response;

import com.example.Librarymanagementsystem.data.model.enums.BookGenre;
import lombok.Data;

@Data
public class BookResponseDTO {

    private String title;
    private String author;
    private BookGenre genre;
    private String language;
}
