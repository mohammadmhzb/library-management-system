package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface IBookService {
    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Stream<Book> getReservableBooks();

    Book addBook(Book book);

    void removeBook(Long id);
}

