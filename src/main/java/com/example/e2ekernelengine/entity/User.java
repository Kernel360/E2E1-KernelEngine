package com.example.e2ekernelengine.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 10, sequenceName = "user_seq")
	@Column(name = "user_id", columnDefinition = "BIGINT", nullable = false)
	private Long userId;

	@Column(name = "user_name", columnDefinition = "VARCHAR(50)", nullable = false)
	private String userName;

	@Column(name = "user_email", columnDefinition = "VARCHAR(100)", nullable = false)
	private String userEmail;

	@Column(name = "user_password", columnDefinition = "VARCHAR(100)", nullable = false)
	private String userPassword;

	@CreationTimestamp
	@Column(name = "user_registered_at")
	private Timestamp userRegisteredAt;

	@Column(name = "user_status_type", columnDefinition = "VARCHAR(20)", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatusType userStatusType = UserStatusType.ACTIVE;

	@Column(name = "user_permission_type", columnDefinition = "VARCHAR(20)", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserPermissionType userPermissionType = UserPermissionType.MEMBER;
}
