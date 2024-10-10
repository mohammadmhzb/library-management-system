package com.example.Librarymanagementsystem.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request payload for user sign-in.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
