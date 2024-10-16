package com.example.Librarymanagementsystem;

import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.request.SignInRequestDTO;
import com.example.Librarymanagementsystem.payload.response.AuthenticationResponse;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.service.impl.BookService;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReservationService reservationService;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        // Perform sign-in to get JWT token
        jwtToken = signIn();
    }

    private String signIn() throws Exception {
        SignInRequestDTO signInRequest = new SignInRequestDTO();
        signInRequest.setEmail("iman"); // Use a valid admin email
        signInRequest.setPassword("password123"); // Use a valid password

        String response = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Deserialize the response to get the token
        AuthenticationResponse authResponse = objectMapper.readValue(response, AuthenticationResponse.class);
        return authResponse.getToken();
    }

    @Test
    void testCreateBook() throws Exception {
        BookRequestDTO bookRequest = new BookRequestDTO();
        bookRequest.setTitle("New Book");
        bookRequest.setAuthor("Author Name");
        // Set other book details

//        Mockito.when(bookService.addBook(Mockito.any(BookRequestDTO.class)))
//                .thenReturn(new Response<>("Book created successfully"));

        mockMvc.perform(post("/books/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetBooks() throws Exception {
        // Mock the behavior of the bookService
//        Mockito.when(bookService.getAllBooks())
//                .thenReturn(new Response<>("List of books"));

        mockMvc.perform(get("/books/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById() throws Exception {
        long bookId = 1;
        // Mock the behavior of the bookService
//        Mockito.when(bookService.getBookById(bookId))
//                .thenReturn(new Response<>("Book details"));

        mockMvc.perform(get("/books/{id}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBook() throws Exception {
        long bookId = 1;

//        Mockito.when(bookService.removeBook(bookId))
//                .thenReturn(new Response<>("Book deleted successfully"));

        mockMvc.perform(delete("/books/{id}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBook() throws Exception {
        long bookId = 1L;
        BookRequestDTO bookRequest = new BookRequestDTO();
        bookRequest.setTitle("Updated Title");
        bookRequest.setAuthor("Updated Author");
        // Set other updated details

//        Mockito.when(bookService.updateBook(Mockito.eq(bookId), Mockito.any(BookRequestDTO.class)))
//                .thenReturn(new Response<>("Book updated successfully"));

        mockMvc.perform(put("/books/{id}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void testPatchBook() throws Exception {
        long bookId = 1;
        BookRequestDTO bookRequest = new BookRequestDTO();
        bookRequest.setTitle("Partially Updated Title");

//        Mockito.when(bookService.patchBook(Mockito.eq(bookId), Mockito.any(BookRequestDTO.class)))
//                .thenReturn(new Response<>("Book partially updated"));

        mockMvc.perform(patch("/books/{id}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk());
    }
}
