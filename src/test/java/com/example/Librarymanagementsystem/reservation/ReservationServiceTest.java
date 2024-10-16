package com.example.Librarymanagementsystem.reservation;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.Reservation;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.model.enums.BookAvailability;
import com.example.Librarymanagementsystem.data.model.enums.ReservationStatus;
import com.example.Librarymanagementsystem.data.model.enums.UserRole;
import com.example.Librarymanagementsystem.data.repository.BookRepository;
import com.example.Librarymanagementsystem.data.repository.ReservationRepository;
import com.example.Librarymanagementsystem.payload.request.ReservationRequest;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.security.services.UserDetailsImpl;
import com.example.Librarymanagementsystem.service.impl.ReservationService;
import com.example.Librarymanagementsystem.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetAllReservations() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        List<Reservation> reservations = Collections.singletonList(reservation);

        when(reservationRepository.findAll()).thenReturn(reservations);

        Response<List<Reservation>> response = reservationService.getAllReservations();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertFalse(response.getMessage().isEmpty());
        assertEquals(1, response.getMessage().size());
    }


    @Test
    void testGetReservationsByUserId() {
        Long userId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        List<Reservation> reservations = Collections.singletonList(reservation);

        when(reservationRepository.findByUserId(userId)).thenReturn(reservations);

        Response<List<Reservation>> response = reservationService.getReservationsByUserId(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertFalse(response.getMessage().isEmpty()); // Assuming message contains reservations info
        assertEquals(1, response.getMessage().size());
    }


    @Test
    void testGetAllReservationsNoData() {
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        Response<List<Reservation>> response = reservationService.getAllReservations();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getMessage().isEmpty());
        assertEquals(0, response.getMessage().size());
    }


    @Test
    void testSaveReservationSuccess() {
        ReservationRequest request = new ReservationRequest();
        request.setBookId(1L);

        Book book = new Book();
        book.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setEmail("email@example.com");
        user.setPassword("password");
        user.setRole(UserRole.valueOf("USER"));

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "email@example.com", "password", authorities);

        when(bookRepository.findById(request.getBookId())).thenReturn(Optional.of(book));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<Reservation> response = reservationService.saveReservation(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertNotNull(response.getMessage());
        assertEquals(userDetails.getId(), response.getMessage().getUser().getId());
    }


    @Test
    void testDeleteReservation() {
        Long reservationId = 1L;

        doNothing().when(reservationRepository).deleteById(reservationId);

        Response<String> response = reservationService.deleteReservation(reservationId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals("Reservation deleted successfully with ID: " + reservationId, response.getMessage());
    }


    @Test
    void testUpdateReservationStatusSuccess() {
        Long reservationId = 1L;
        ReservationStatus status = ReservationStatus.APPROVED;

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        Book book = new Book();
        reservation.setBook(book);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<Reservation> response = reservationService.updateReservationStatus(reservationId, status);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(status, response.getMessage().getStatus());
        assertEquals(BookAvailability.RESERVED, reservation.getBook().getAvailability());
    }
}
