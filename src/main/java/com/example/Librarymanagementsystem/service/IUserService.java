package com.example.Librarymanagementsystem.service;

import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.Response;
import com.example.Librarymanagementsystem.payload.response.UserResponseDTO;

import java.util.List;

public interface IUserService {
    Response<List<User>> getAllUsers();

    Response<UserResponseDTO> getUserById(Long id);

    Response<String> createUser(UserRequestDTO user);

    Response<String> updateUser(Long id, UserRequestDTO userDetails);

    Response<String> patchUser(Long id, UserRequestDTO userDetails);

    Response<String> deleteUser(Long id);
}
