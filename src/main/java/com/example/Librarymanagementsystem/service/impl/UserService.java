package com.example.Librarymanagementsystem.service.impl;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.exception.DuplicateEntryException;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.mapper.BookMapper;
import com.example.Librarymanagementsystem.payload.mapper.UserMapper;
import com.example.Librarymanagementsystem.payload.request.BookRequestDTO;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.payload.response.UserResponseDTO;
import com.example.Librarymanagementsystem.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response<String> createUser(UserRequestDTO userRequestDTO) {

        User user = UserMapper.toEntity(userRequestDTO);

        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            log.warn("Attempted to add a duplicate user: {} {}", user.getFirstName(), user.getLastName());
            throw new DuplicateEntryException("User already exists with the same username or email");
        }

        log.info("Saving user: {}", user);
        userRepository.save(user);

        String message = "user added successfully: " + user.getFirstName() + " " + user.getLastName();
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                message
        );
    }

    public Response<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Retrieved {} users from the repository.", users.size());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                users
        );
    }

    public Response<UserResponseDTO> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->{
                    log.warn("User with ID {} not found", id);
                    return new ResourceNotFoundException("User with ID " + id + " not found.");
                });

        log.info("Successfully retrieved user with ID {}: {} {}",
                id, user.getFirstName(), user.getLastName());

        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                UserMapper.toDTO(user)
        );
    }

    public Response<String> updateUser(Long id, UserRequestDTO userRequestDTO) {

        if (!userRepository.existsById(id)) {
            log.warn("User with ID {} not found", id);
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        User userDetails = UserMapper.toEntity(userRequestDTO);

        Optional<User> updatedUser= userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setRole(userDetails.getRole());
            existingUser.setUpdatedAt(Instant.now());
            return userRepository.save(existingUser);
        });

        log.info("User updated successfully: {}", updatedUser.get());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "User updated successfully with ID " + id
        );
    }

    public Response<String> patchUser(Long id, UserRequestDTO userRequestDTO) {

        if (!userRepository.existsById(id)) {
            log.warn("User with ID {} not found", id);
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        User userDetails = UserMapper.toEntity(userRequestDTO);

        Optional<User> updatedUser = userRepository.findById(id).map(existingUser -> {
            if (userDetails.getFirstName() != null) {
                existingUser.setFirstName(userDetails.getFirstName());
            }
            if (userDetails.getLastName() != null) {
                existingUser.setLastName(userDetails.getLastName());
            }
            if (userDetails.getUsername() != null) {
                existingUser.setUsername(userDetails.getUsername());
            }
            if (userDetails.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            }
            if (userDetails.getPassword() != null) {
                existingUser.setPassword(userDetails.getPassword());
            }
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getRole() != null) {
                existingUser.setRole(userDetails.getRole());
            }
            existingUser.setUpdatedAt(Instant.now());

            return userRepository.save(existingUser);
        });

        log.info("User patched successfully: {}", updatedUser.get());
        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "User updated successfully with ID " + id
        );

    }

    public Response<String> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            log.warn("User with ID {} not found", id);
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);

        return new Response<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "User deleted successfully with ID: " +id
        );
    }
}
