package com.dev.springsecurity.service;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.entity.User;

import java.util.Optional;

public interface UserService {

	User saveUser(UserDTO userDTO);

	void saveUserVerificationToken(User user, String token);

	String verifyUserToken(String token);

	Optional<User> resendUserToken(String oldToken);

}
