package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User userDetails);

    void deleteUser(Long id);
}
