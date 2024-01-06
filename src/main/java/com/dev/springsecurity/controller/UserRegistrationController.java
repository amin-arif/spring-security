package com.dev.springsecurity.controller;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegistrationController {

	private final UserService userService;

	@PostMapping("user/create")
	public HttpStatus registerUser(
			@RequestBody UserDTO userDTO,
			HttpServletRequest httpServletRequest) {
		return userService.saveUser(userDTO)
				? HttpStatus.CREATED
				: HttpStatus.BAD_REQUEST;
	}

}
