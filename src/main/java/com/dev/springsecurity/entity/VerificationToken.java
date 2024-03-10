package com.dev.springsecurity.entity;

import com.dev.springsecurity.util.CommonUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
		this.expirationTime = CommonUtil.generateExpirationTime(5);
	}

	public VerificationToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expirationTime = CommonUtil.generateExpirationTime(5);
	}


}
