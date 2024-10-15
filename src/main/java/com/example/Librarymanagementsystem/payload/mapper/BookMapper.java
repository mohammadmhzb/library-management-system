package com.example.Librarymanagementsystem.payload.mapper;

import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;

public class BookMapper {
    public static Book toEntity(BookRequestDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPages(bookDTO.getPages());
        book.setGenre(bookDTO.getGenre());
        book.setLanguage(bookDTO.getLanguage());
        return book;
    }


    public static BookResponseDTO toDTO(Book book) {
        BookResponseDTO bookDTO = new BookResponseDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setLanguage(book.getLanguage());
        return bookDTO;
    }
}
