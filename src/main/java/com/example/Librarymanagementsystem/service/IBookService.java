package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import java.util.List;

public interface IBookService {

    Response<String> addBook(BookRequestDTO book);

    Response<List<Book>> getAllBooks();

    Response<BookResponseDTO> getBookById(Long id);

    Response<List<BookResponseDTO>> getAvailableBooks();

    Response<String> removeBook(Long id);

    Response<String> updateBook(Long id, BookRequestDTO book);

    Response<String> patchBook(Long id, BookRequestDTO book);

}

