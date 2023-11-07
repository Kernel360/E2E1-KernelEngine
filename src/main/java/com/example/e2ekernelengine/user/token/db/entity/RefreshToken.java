package com.example.e2ekernelengine.user.token.db.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.e2ekernelengine.user.db.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_token")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refresh_token_id", columnDefinition = "BIGINT", nullable = false)
	private long refreshTokenId;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@Column(name = "refresh_token", nullable = false, unique = true)
	private String refreshToken;

	@Column(name = "refresh_token_expired_at", nullable = false)
	private Timestamp refreshTokenExpiredAt;
}
