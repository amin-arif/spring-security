package com.dev.springsecurity.controller;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.event.RegistrationCompleteEvent;
import com.dev.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegistrationController {

	private final UserService userService;
	private final ApplicationEventPublisher applicationEventPublisher;

	@PostMapping("user/create")
	public HttpStatus registerUser(
			@RequestBody UserDTO userDTO,
			HttpServletRequest httpServletRequest) {
		User user = userService.saveUser(userDTO);
		if (user != null) {
			applicationEventPublisher.publishEvent(
					new RegistrationCompleteEvent(
							user,
							getApplicationUrl(httpServletRequest)
					)
			);
			return HttpStatus.CREATED;
		}
		return HttpStatus.BAD_REQUEST;
	}

	private String getApplicationUrl(HttpServletRequest request) {
		return "https://"
				+ request.getServerName()
				+ ":"
				+ request.getServerPort()
				+ request.getContextPath();
	}

}
