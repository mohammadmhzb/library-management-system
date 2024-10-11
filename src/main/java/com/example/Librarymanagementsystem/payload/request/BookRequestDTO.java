package com.example.Librarymanagementsystem.payload.request;

import com.example.Librarymanagementsystem.data.model.enums.BookGenre;
import lombok.Data;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class BookRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+[a-zA-Z0-9]*$", message = "Title must be a string and cannot contain numbers")
    @Size(max = 100, message = "Title length should not exceed 100 characters")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    @Pattern(regexp = "^[a-zA-Z.\\s]+$", message = "Title must be a string and cannot contain numbers")
    @Size(max = 30, message = "Author name length should not exceed 30 characters")
    private String author;

    @Min(value = 1, message = "Pages must be a positive number")
    @Max(value = 10000, message = "Page count cannot exceed 10000 pages")
    private int pages;

    @NotNull(message = "Genre cannot be null")
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @NotBlank(message = "Language cannot be blank")
    @Size(max = 20, message = "Language length should not exceed 20 characters")
    private String language;


}
