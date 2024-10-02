package com.example.Librarymanagementsystem.model;
import com.example.Librarymanagementsystem.model.audit.DateAudit;
import com.example.Librarymanagementsystem.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.model.enums.BookGenre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Book extends DateAudit {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier for the book", example = "1")
    private Long id;

    @NotBlank
    @Column(name = "title")
    @Schema(description = "Title of the book", example = "The Great Gatsby")
    private String title;

    @NotBlank
    @Column(name = "author")
    @Schema(description = "Author of the book", example = "F. Scott Fitzgerald")
    private String author;

    @Min(1)
    @Column(name = "pages")
    @Schema(description = "Number of pages in the book", example = "180")
    private int pages;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    @Schema(description = "Genre of the book", example = "FICTION", allowableValues = {"FICTION",
            "NON_FICTION",
            "MYSTERY",
            "SCIENCE_FICTION",
            "FANTASY",
            "BIOGRAPHY",
            "HISTORY",
            "ROMANCE",
            "THRILLER"})
    private BookGenre genre;

    @NotBlank
    @Column(name = "language")
    @Schema(description = "Language in which the book is written", example = "English")
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    @Schema(description = "Availability status of the book", example = "AVAILABLE", allowableValues = {"AVAILABLE", "CHECKED_OUT", "RESERVED"})
    private BookAvailability availability = BookAvailability.AVAILABLE;

    @Version
    private  Integer version;


    public Book(String title, String author, int pages, BookGenre genre, String language, BookAvailability availability) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genre = genre;
        this.language = language;
        this.availability = availability;
    }
}
