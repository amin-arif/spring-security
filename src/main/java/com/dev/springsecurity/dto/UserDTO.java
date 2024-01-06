package com.dev.springsecurity.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.dev.springsecurity.entity.User}
 */
public record UserDTO(
		String firstName,
		String lastName,
		String email,
		String password,
		String role
) implements Serializable {

}
