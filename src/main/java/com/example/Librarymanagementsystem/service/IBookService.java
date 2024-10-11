package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.response.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface IBookService {
    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Stream<Book> getReservableBooks();

    Response<String> addBook(BookRequestDTO book);

    void removeBook(Long id);

    List<Book> getAvailableBooks();
}

