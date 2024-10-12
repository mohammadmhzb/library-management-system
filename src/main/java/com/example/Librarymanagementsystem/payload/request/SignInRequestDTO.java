package com.example.Librarymanagementsystem.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

/**
 * Request payload for user sign-in.
 */

@Data
public class SignInRequestDTO {

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	@Size(max = 30)
	private String email;

	@NotBlank(message = "Password cannot be blank")
	@Size(min = 6, max = 6, message = "Password must be exactly 6 characters long")
	private String password;
}
