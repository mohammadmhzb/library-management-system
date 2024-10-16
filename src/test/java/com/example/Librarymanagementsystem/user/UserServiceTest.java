package com.example.Librarymanagementsystem.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.exception.DuplicateEntryException;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.mapper.UserMapper;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.payload.response.UserResponseDTO;
import com.example.Librarymanagementsystem.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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


    @Test
    void getAllUsers_ShouldReturnAvailableUsers() {
        List<User> expectedUsers = Collections.singletonList(user1);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        Response<List<User>> response = userService.getAllUsers();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedUsers.size(), response.getMessage().size());
        assertEquals(expectedUsers, response.getMessage());

        verify(userRepository, times(1)).findAll();
    }


    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Response<UserResponseDTO> response = userService.getUserById(1L);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getMessage());
        assertEquals(user1.getFirstName(), response.getMessage().getFirstName());
        assertEquals(user1.getLastName(), response.getMessage().getLastName());

        verify(userRepository, times(1)).findById(1L);
    }


    @Test
    void getUserById_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("User with ID 1 not found.", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
    }


    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        Response<String> response = userService.deleteUser(1L);

        assertNotNull(response);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("User deleted successfully with ID: 1", response.getMessage());
        verify(userRepository, times(1)).deleteById(1L);
    }


    @Test
    void deleteUser_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });

        assertEquals("User with ID 1 not found.", exception.getMessage());
        verify(userRepository, never()).deleteById(1L);
    }


    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);

        Response<String> response = userService.updateUser(1L, userRequestDTO1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("User updated successfully with ID 1", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));

    }


    @Test
    void updateUser_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(1L, userRequestDTO1);
        });

        assertEquals("User with ID 1 not found.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void patchUser_ShouldPatchUser_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);

        Response<String> response = userService.patchUser(1L, userRequestDTO1);


        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("User updated successfully with ID 1", response.getMessage());

        assertEquals("John", user1.getFirstName());
        assertEquals("Doe", user1.getLastName());
        assertEquals("johndoe", user1.getUsername());
        assertEquals("01234567891", user1.getPhoneNumber());
        assertEquals("john.doe@example.com", user1.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void patchUser_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.patchUser(1L, userRequestDTO1);
        });

        assertEquals("User with ID 1 not found.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
