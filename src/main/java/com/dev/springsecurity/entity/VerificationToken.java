package com.dev.springsecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
	// Expiration time in minutes
	@Transient
	@Value("${token.expiration.time}")
	private final int TOKEN_EXPIRATION_TIME = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	private Date expirationTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
	private User user;

	public VerificationToken(String token) {
		this.token = token;
		this.expirationTime = generateExpirationTime();
	}

	public VerificationToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expirationTime = generateExpirationTime();
	}

	private Date generateExpirationTime() {
//		LocalDateTime localDateTime = LocalDateTime.now();
//		localDateTime.plusMinutes(TOKEN_EXPIRATION_TIME);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, TOKEN_EXPIRATION_TIME);
		return new Date(calendar.getTime().getTime());
	}
}
