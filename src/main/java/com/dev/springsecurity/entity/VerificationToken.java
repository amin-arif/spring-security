package com.dev.springsecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	private Date expirationTime;

	@ManyToOne(fetch = FetchType.EAGER)
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
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		int tokenExpirationTime = 5; // In minutes
		calendar.add(Calendar.MINUTE, tokenExpirationTime);
		return new Date(calendar.getTime().getTime());
	}
}
