package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.payload.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Response<List<User>> getAllUsers();

    Response<UserResponseDTO> getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User userDetails);

    Response<String> deleteUser(Long id);
}
