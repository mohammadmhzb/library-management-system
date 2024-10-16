package com.example.Librarymanagementsystem.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.exception.DuplicateEntryException;
import com.example.Librarymanagementsystem.payload.mapper.UserMapper;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequestDTO1;
    private User user1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        userRequestDTO1 = new UserRequestDTO();
        userRequestDTO1.setFirstName("John");
        userRequestDTO1.setLastName("Doe");
        userRequestDTO1.setUsername("johndoe");
        userRequestDTO1.setPhoneNumber("01234567891");
        userRequestDTO1.setPassword("password123");
        userRequestDTO1.setEmail("john.doe@example.com");


        user1 = UserMapper.toEntity(userRequestDTO1);

    }

    @Test
    void addUser_ShouldSaveUser_WhenUserIsNew() {
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(null);
        Response<String> response1 = userService.createUser(userRequestDTO1);
        assertNotNull(response1);
        assertEquals(HttpStatus.CREATED.value(), response1.getStatus());
        assertEquals("user added successfully: John Doe", response1.getMessage());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void addUser_ShouldThrowDuplicateEntryException_WhenUserAlreadyExists() {
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(true);
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(false);

        DuplicateEntryException exception1 = assertThrows(DuplicateEntryException.class, () -> {
            userService.createUser(userRequestDTO1);
        });
        assertEquals("User already exists with the same username or email", exception1.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

}
