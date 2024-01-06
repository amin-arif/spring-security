package com.dev.springsecurity.service;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Boolean saveUser(UserDTO userDTO) {
		User user = prepareUser(userDTO);
		Optional<User> savedUser = Optional.of(userRepository.save(user));
		return savedUser.isPresent();
	}

	private User prepareUser(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.firstName());
		user.setLastName(userDTO.lastName());
		user.setEmail(userDTO.email());
		user.setRole("ROLE_USER");
		user.setPassword(
				passwordEncoder.encode(userDTO.password())
		);
		return user;
	}
}
