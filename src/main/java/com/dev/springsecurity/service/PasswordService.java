package com.dev.springsecurity.service;

import com.dev.springsecurity.dto.PasswordDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface PasswordService {

	String forgetUserPassword(HttpServletRequest request, String email);

	String verifyUserNewPassword(String token, String newPassword);

	String changeUserPassword(PasswordDTO dto);
}
