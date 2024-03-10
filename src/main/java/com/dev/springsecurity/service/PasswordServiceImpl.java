package com.dev.springsecurity.service;

import com.dev.springsecurity.entity.ForgetPasswordToken;
import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.repository.ForgetPasswordTokenRepository;
import com.dev.springsecurity.repository.UserRepository;
import com.dev.springsecurity.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PasswordServiceImpl implements PasswordService {

	private final UserRepository userRepository;
	private final ForgetPasswordTokenRepository passwordTokenRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String forgetUserPassword(HttpServletRequest request, String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			return "Invalid email";
		}

		String token = CommonUtil.generateToken();
		ForgetPasswordToken passwordToken= new ForgetPasswordToken(user.get(), token);
		passwordTokenRepository.save(passwordToken);

		String url = CommonUtil.getApplicationUrl(request)
				+ "/user/password/verify?token="
				+ token;

		log.info("Click link to enter new password: {}", url);
		return "Sent link for new password";
	}

	@Override
	public String verifyUserNewPassword(String token, String newPassword) {
		Optional<ForgetPasswordToken> passwordToken = passwordTokenRepository.findByToken(token);
		if (passwordToken.isEmpty()) {
			return "Invalid token";
		}

		if (CommonUtil.isTimeExpire(passwordToken.get().getExpirationTime())) {
			return "Forget token expired";
		}

		User user = passwordToken.get().getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		return "New password set successfully";
	}
}
