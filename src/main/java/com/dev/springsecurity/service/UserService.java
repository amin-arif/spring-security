package com.dev.springsecurity.service;

import com.dev.springsecurity.dto.UserDTO;
import com.dev.springsecurity.entity.User;
import com.dev.springsecurity.entity.VerificationToken;

public interface UserService {

	User saveUser(UserDTO userDTO);

	void saveUserVerificationToken(User user, String token);
}
