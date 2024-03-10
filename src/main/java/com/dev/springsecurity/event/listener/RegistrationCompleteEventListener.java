package com.dev.springsecurity.event.listener;

import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.event.RegistrationCompleteEvent;
import com.dev.springsecurity.service.UserService;
import com.dev.springsecurity.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationCompleteEventListener
		implements ApplicationListener<RegistrationCompleteEvent> {

	private final UserService userService;

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = CommonUtil.generateToken();
		userService.saveUserVerificationToken(user, token);

		String url = event.getApplicationUrl()
				+ "/user/verify?token="
				+ token;
		// Further it will send by Email
		log.info("Click link to verify your account: {}", url);
	}
}
