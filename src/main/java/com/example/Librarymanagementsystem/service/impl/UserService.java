package com.example.Librarymanagementsystem.service.impl;

import com.example.Librarymanagementsystem.data.model.Book;
import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.data.repository.UserRepository;
import com.example.Librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.Librarymanagementsystem.payload.mapper.BookMapper;
import com.example.Librarymanagementsystem.payload.mapper.UserMapper;
import com.example.Librarymanagementsystem.payload.response.BookResponseDTO;
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

    public User createUser(User user) {
        log.info(user.toString());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setUpdatedAt(Instant.now());
        return userRepository.save(user);
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
