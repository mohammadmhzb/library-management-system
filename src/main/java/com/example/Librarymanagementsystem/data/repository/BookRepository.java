package com.example.Librarymanagementsystem.data.repository;

import com.example.Librarymanagementsystem.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    Optional<Book> findById(Long id);

    List<Book> findByAuthor(String author);

    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthorContaining(String author);

    List<Book> findByTitleStartingWith(String title);

    List<Book> findByTitleEndingWith(String title);

    List<Book> findByTitleContainingAndAuthorContaining(String title, String author);

    List<Book> findByAuthorContainingAndTitleContaining(String author, String title);

    List<Book> findByLanguage(String language);
}