package com.dev.springsecurity.dto;

/**
 * DTO for Password
 */
public record PasswordDTO(
		String email,
		String newPassword,
		String oldPassword)
{
}
