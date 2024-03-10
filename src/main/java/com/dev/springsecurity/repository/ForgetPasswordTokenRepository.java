package com.dev.springsecurity.repository;

import com.dev.springsecurity.entity.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgetPasswordTokenRepository extends JpaRepository<ForgetPasswordToken, Long> {

	Optional<ForgetPasswordToken> findByToken(String token);

}
