package com.example.Librarymanagementsystem.payload.request;

import com.example.Librarymanagementsystem.data.model.enums.UserRole;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;


@Data
public class UserRequestDTO {

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name cannot contain digits or special characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name cannot contain digits or special characters")
    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 15)
    private String username;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 11, max = 11, message = "Phone number must be exactly 11 digits")
    @Pattern(regexp = "^0\\d{10}$", message = "Phone number must be 11 digits and start with 0")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 30)
    private String email;

    @NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
}





