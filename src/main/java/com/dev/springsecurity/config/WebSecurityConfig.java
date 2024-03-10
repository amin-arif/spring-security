package com.dev.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	private static final String[] AUTH_WHITE_LIST_URLS = {
			"/user/create",
			"/user/verify/**",
			"/user/token/resend*",
			"user/password/forget*",
			"user/password/verify*",
			"user/password/change*"
	};

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.cors()
				.and()
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.requestMatchers(AUTH_WHITE_LIST_URLS)
				.permitAll();
		return httpSecurity.build();
	}
}
