package com.example.e2ekernelengine.domain.user.db.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.example.e2ekernelengine.domain.user.util.UserPermissionType;
import com.example.e2ekernelengine.domain.user.util.UserStatusType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", columnDefinition = "BIGINT", nullable = false)
	private Long userId;

	@Column(name = "user_name", columnDefinition = "VARCHAR(50)", nullable = false)
	private String userName;

	@Column(name = "user_email", columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
	private String userEmail;

	@Column(name = "user_password", columnDefinition = "VARCHAR(100)", nullable = false)
	private String userPassword;

	@CreationTimestamp
	@Column(name = "user_registered_at", columnDefinition = "TIMESTAMP")
	private Timestamp userRegisteredAt;

	@Column(name = "user_status_type", columnDefinition = "VARCHAR(20)", nullable = false)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private UserStatusType userStatusType = UserStatusType.ACTIVE;

	@Column(name = "user_permission_type", columnDefinition = "VARCHAR(20)", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserPermissionType userPermissionType;

	public void leave() {
		this.userStatusType = UserStatusType.WITHDRAWAL;
	}

}
