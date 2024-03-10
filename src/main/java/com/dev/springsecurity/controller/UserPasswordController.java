package com.dev.springsecurity.controller;

import com.dev.springsecurity.dto.PasswordDTO;
import com.dev.springsecurity.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserPasswordController {

	private final PasswordService passwordService;

	@PostMapping("/user/password/forget")
	public String forgetPassword(HttpServletRequest request,
								 @RequestParam String email) {
		return passwordService.forgetUserPassword(request, email);
	}

	@PutMapping("/user/password/verify")
	public String verifyNewPassword(@RequestParam String token, @RequestBody PasswordDTO dto) {
		return passwordService.verifyUserNewPassword(token, dto.newPassword());
	}

}
