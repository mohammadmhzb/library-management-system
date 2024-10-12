package com.example.Librarymanagementsystem;

import com.example.Librarymanagementsystem.controller.AuthController;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.model.enums.UserRole;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.payload.request.SignInRequest;
import com.example.Librarymanagementsystem.payload.request.SignUpRequest;
import com.example.Librarymanagementsystem.payload.response.AuthenticationResponse;
import com.example.Librarymanagementsystem.payload.response.MessageResponse;
import com.example.Librarymanagementsystem.security.jwt.JwtUtils;
import com.example.Librarymanagementsystem.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser_Success() {
        SignInRequest signInRequest = new SignInRequest("test@example.com", "password");
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@example.com", "password", Collections.emptyList());
        String jwtToken = "jwt.token.here";

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(userDetails)).thenReturn(jwtToken);

        ResponseEntity<?> response = authController.authenticateUser(signInRequest);

        assertEquals(200, response.getStatusCodeValue());
        AuthenticationResponse authResponse = (AuthenticationResponse) response.getBody();
        assertEquals(jwtToken, authResponse.getToken());
        assertEquals(userDetails.getId(), authResponse.getId());
        assertEquals(userDetails.getEmail(), authResponse.getEmail());
    }

    @Test
    public void testRegisterUser_Success() {
        SignUpRequest signUpRequest = new SignUpRequest("asdad", "username", "asdasd", "password1234", "test@example.com", UserRole.USER.name());
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        assertEquals(200, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("User Registered Successfully!", messageResponse.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogoutUser_Success() {
        ResponseCookie cookie = ResponseCookie.from("JWT", "").path("/").httpOnly(true).build();
        when(jwtUtils.getCleanJwtCookie()).thenReturn(cookie);

        ResponseEntity<?> response = authController.logoutUser();

        assertEquals(200, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("You've Been Signed Out!", messageResponse.getMessage());
        assertEquals(cookie.toString(), response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
    }
}
