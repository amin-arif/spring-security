package com.dev.springsecurity.service;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.entity.VerificationToken;
import com.dev.springsecurity.repository.UserRepository;
import com.dev.springsecurity.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		VerificationToken verificationToken = new VerificationToken(user, token);
		verificationTokenRepository.save(verificationToken);
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
