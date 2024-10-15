package com.example.Librarymanagementsystem.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(max = 40)
    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @NotBlank
    @Size(max = 40)
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @NotBlank
    @Size(max = 15)
    @Schema(description = "Username of the user", example = "johndoe")
    private String username;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Password for the user account", example = "password123")
    private String password;

    @NotBlank
    @Email
    @Size(max = 40)
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Role of the user in the system", example = "USER", allowableValues = {"ADMIN", "MANAGER", "USER"}, defaultValue = "USER")
    private String role = "USER"; // Default role
}
