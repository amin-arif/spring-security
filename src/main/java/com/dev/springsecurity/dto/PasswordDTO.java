package com.dev.springsecurity.dto;

/**
 * DTO for Password
 */
public record PasswordDTO(
		String newPassword,
		String oldPassword)
{
	public PasswordDTO {
	}
}
