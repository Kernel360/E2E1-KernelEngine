package com.example.e2ekernelengine.user.dto.response;

import java.sql.Timestamp;

import com.example.e2ekernelengine.user.util.UserPermissionType;
import com.example.e2ekernelengine.user.util.UserStatusType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
	private Long userId;

	private String userName;

	private String userEmail;

	private Timestamp userRegisteredAt;

	private UserStatusType userStatusType;

	private UserPermissionType userPermissionType;
}
