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
public class ForgetPasswordToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	private Date expirationTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_FORGET_PASSWORD_TOKEN"))
	private User user;

	public ForgetPasswordToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expirationTime = CommonUtil.generateExpirationTime(5);
	}


}
