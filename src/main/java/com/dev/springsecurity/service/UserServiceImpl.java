package com.dev.springsecurity.service;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.entity.VerificationToken;
import com.dev.springsecurity.repository.UserRepository;
import com.dev.springsecurity.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;

	@Override
	public User saveUser(UserDTO userDTO) {
		User user = prepareUser(userDTO);
		return userRepository.save(user);
	}

	@Override
	public void saveUserVerificationToken(User user, String token) {
		verificationTokenRepository.save(
				new VerificationToken(user, token)
		);
	}

	@Override
	public String verifyUserToken(String token) {
		Optional<VerificationToken> verificationToken =
				verificationTokenRepository.findByToken(token);
		if (verificationToken.isEmpty()) {
			return "Invalid token";
		}

		Date currentTime = Calendar.getInstance().getTime();
		if (currentTime.getTime() - verificationToken.get().getExpirationTime().getTime() >= 0) {
			return "Token expired";
		}

		User user = verificationToken.get().getUser();
		user.setEnabled(Boolean.TRUE);
		userRepository.save(user);
		return "User verified";
	}

	@Override
	public Optional<User> resendUserToken(String oldToken) {
		Optional<VerificationToken> token = verificationTokenRepository.findByToken(oldToken);
		if (token.isEmpty()) {
			return Optional.empty();
		}
		User user = token.get().getUser();
		verificationTokenRepository.delete(token.get());
		return Optional.of(user);
	}

	private User prepareUser(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.firstName());
		user.setLastName(userDTO.lastName());
		user.setEmail(userDTO.email());
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(userDTO.password()));
		return user;
	}
}
