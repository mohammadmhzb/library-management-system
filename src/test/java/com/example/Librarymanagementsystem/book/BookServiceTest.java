package com.example.Librarymanagementsystem.book;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.model.enums.BookGenre;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.exception.DuplicateEntryException;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.mapper.BookMapper;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.service.impl.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private List<Book> books;
    private BookRequestDTO bookRequestDTO1;
    private BookRequestDTO bookRequestDTO2;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookRequestDTO1 = new BookRequestDTO();
        bookRequestDTO1.setTitle("Book 1984");
        bookRequestDTO1.setAuthor("George Orwell");
        bookRequestDTO1.setPages(328);
        bookRequestDTO1.setGenre(BookGenre.FICTION);
        bookRequestDTO1.setLanguage("English");
        book1 = BookMapper.toEntity(bookRequestDTO1);

        bookRequestDTO2 = new BookRequestDTO();
        bookRequestDTO2.setTitle("Sapiens: A Brief History of Humankind");
        bookRequestDTO2.setAuthor("Yuval Noah Harari");
        bookRequestDTO2.setPages(443);
        bookRequestDTO2.setGenre(BookGenre.HISTORY);
        bookRequestDTO2.setLanguage("English");
        book2 = BookMapper.toEntity(bookRequestDTO2);

        books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
    }


    @Test
    void addBook_ShouldSaveBook_WhenBookIsNew() {
        // First test for book1
        when(bookRepository.findByTitle(book1.getTitle())).thenReturn(null);
        Response<String> response1 = bookService.addBook(bookRequestDTO1);
        assertNotNull(response1);
        assertEquals(HttpStatus.CREATED.value(), response1.getStatus());
        assertEquals("Book added successfully: Book 1984", response1.getMessage());
        verify(bookRepository, times(1)).save(any(Book.class));

        // First test for book2
        when(bookRepository.findByTitle(book2.getTitle())).thenReturn(null);
        Response<String> response2 = bookService.addBook(bookRequestDTO2);
        assertNotNull(response2);
        assertEquals(HttpStatus.CREATED.value(), response2.getStatus());
        assertEquals("Book added successfully: Sapiens: A Brief History of Humankind", response2.getMessage());
        verify(bookRepository, times(2)).save(any(Book.class));
    }


    @Test
    void addBook_ShouldThrowDuplicateEntryException_WhenBookAlreadyExists() {
        // Test for duplicate book1
        when(bookRepository.findByTitle(book1.getTitle())).thenReturn(book1);
        DuplicateEntryException exception1 = assertThrows(DuplicateEntryException.class, () -> {
            bookService.addBook(bookRequestDTO1);
        });
        assertEquals("Book already exists with the same title: Book 1984", exception1.getMessage());
        verify(bookRepository, never()).save(any(Book.class));


        // Test for duplicate book2
        when(bookRepository.findByTitle(book2.getTitle())).thenReturn(book2);
        DuplicateEntryException exception2 = assertThrows(DuplicateEntryException.class, () -> {
            bookService.addBook(bookRequestDTO2);
        });
        assertEquals("Book already exists with the same title: Sapiens: A Brief History of Humankind", exception2.getMessage());
        verify(bookRepository, never()).save(any(Book.class));
    }


    @Test
    void getAllBooks_ShouldReturnAvailableBooks() {
        when(bookRepository.findAll()).thenReturn(books);

        book1.setAvailability(BookAvailability.AVAILABLE);
        book2.setAvailability(BookAvailability.AVAILABLE);

        Response<List<BookResponseDTO>> response = bookService.getAllBooks();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(2, response.getMessage().size());
        verify(bookRepository, times(1)).findAll();
    }


    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        // Test for book1
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        Response<BookResponseDTO> response1 = bookService.getBookById(1L);
        assertNotNull(response1);
        assertEquals(HttpStatus.OK.value(), response1.getStatus());
        assertEquals(book1.getTitle(), response1.getMessage().getTitle());
        verify(bookRepository, times(1)).findById(1L);

        // Test for book2
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));
        Response<BookResponseDTO> response2 = bookService.getBookById(2L);
        assertNotNull(response2);
        assertEquals(HttpStatus.OK.value(), response2.getStatus());
        assertEquals(book2.getTitle(), response2.getMessage().getTitle());
        verify(bookRepository, times(1)).findById(2L);
    }


    @Test
    void getBookById_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
        // Mock repository to return an empty Optional when book with ID 1 does not exist
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception1 = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(1L);
        });
        assertEquals("Book with ID 1 not found.", exception1.getMessage());
        verify(bookRepository, times(1)).findById(1L);


        // Mock repository to return an empty Optional when book with ID 2 does not exist
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception2 = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(2L);
        });
        assertEquals("Book with ID 2 not found.", exception2.getMessage());
        verify(bookRepository, times(1)).findById(2L);
    }


}
