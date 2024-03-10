package com.dev.springsecurity.service;

import jakarta.servlet.http.HttpServletRequest;

public interface PasswordService {

	String forgetUserPassword(HttpServletRequest request, String email);

	String verifyUserNewPassword(String token, String newPassword);
}
