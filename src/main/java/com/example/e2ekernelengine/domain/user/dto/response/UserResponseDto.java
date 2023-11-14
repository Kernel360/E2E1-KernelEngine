package com.example.e2ekernelengine.domain.user.dto.response;

import java.sql.Timestamp;
import java.util.Optional;

import com.example.e2ekernelengine.domain.user.db.entity.User;
import com.example.e2ekernelengine.domain.user.util.UserPermissionType;
import com.example.e2ekernelengine.domain.user.util.UserStatusType;
import com.example.e2ekernelengine.global.exception.NotFoundException;

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

	public static UserResponseDto fromEntity(User user) {
		return Optional.ofNullable(user)
				.map(it -> UserResponseDto.builder()
						.userId(it.getUserId())
						.userName(it.getUserName())
						.userEmail(it.getUserEmail())
						.userRegisteredAt(it.getUserRegisteredAt())
						.userStatusType(it.getUserStatusType())
						.userPermissionType(it.getUserPermissionType())
						.build())
				.orElseThrow(() -> new NotFoundException("User Entity is Null."));
	}
}
