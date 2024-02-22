package com.dev.springsecurity.controller;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.event.RegistrationCompleteEvent;
import com.dev.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserRegistrationController {

	private final UserService userService;
	private final ApplicationEventPublisher applicationEventPublisher;

	@PostMapping("/user/create")
	public HttpStatus createUser(
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

	@GetMapping("/user/verify")
	public String verifyUser(@RequestParam("token") String token) {
		return userService.verifyUserToken(token);
	}

	@GetMapping("/user/token/resend")
	public void tokenResend(@RequestParam("token") String oldToken,
							  HttpServletRequest request) {
		userService.resendUserToken(oldToken).ifPresentOrElse(
				user -> applicationEventPublisher.publishEvent(
						new RegistrationCompleteEvent(
								user,
								getApplicationUrl(request)
						)
				),
				() -> {
					throw new RuntimeException("Invalid token");
				}
		);
	}

	private String getApplicationUrl(HttpServletRequest request) {
		return "http://"
				+ request.getServerName()
				+ ":"
				+ request.getServerPort()
				+ request.getContextPath();
	}

}
