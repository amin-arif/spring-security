package com.dev.springsecurity.dto;

import java.util.regex.Pattern;

/**
 * DTO for {@link com.dev.springsecurity.entity.User}
 */
public record UserDTO(
		String firstName,
		String lastName,
		String email,
		String password,
		String role)
{
	public UserDTO {
		validateEmail(email);
		validatePassword(password);
	}

	private static void validateEmail(String email) {
		String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if (email == null || !Pattern.matches(emailPattern, email)) {
			throw new IllegalArgumentException("Invalid email");
		}
	}

	private static void validatePassword(String password) {
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password must not be empty or null");
		}
	}
}
