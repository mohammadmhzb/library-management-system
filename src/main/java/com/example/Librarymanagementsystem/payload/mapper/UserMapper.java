package com.example.Librarymanagementsystem.payload.mapper;

import com.example.Librarymanagementsystem.data.model.User;
import com.example.Librarymanagementsystem.payload.request.UserRequestDTO;
import com.example.Librarymanagementsystem.payload.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    private static PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        UserMapper.passwordEncoder = passwordEncoder;
    }

    public static User toEntity(UserRequestDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
