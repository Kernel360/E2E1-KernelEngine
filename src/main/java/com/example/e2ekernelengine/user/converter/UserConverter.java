package com.example.e2ekernelengine.user.converter;

import java.util.Optional;

import javax.validation.Valid;

import com.example.e2ekernelengine.global.annotation.Converter;
import com.example.e2ekernelengine.global.exception.NotFoundException;
import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.user.dto.response.UserResponseDto;

import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class UserConverter {

	public User toEntity(@Valid Optional<UserRegisterRequestDto> userRegisterRequestDto) {
		return userRegisterRequestDto
				.map(dto -> User.builder()
						.userName(dto.getUserName())
						.userEmail(dto.getUserEmail())
						.userPassword(dto.getUserPassword())
						.build())
				.orElseThrow(() -> new NotFoundException("UserRegisterRequest Null."));
	}

	public UserResponseDto toDto(User user) {
		return Optional.ofNullable(user)
				.map(it -> {
					return UserResponseDto.builder()
							.userId(user.getUserId())
							.userName(user.getUserName())
							.userEmail(user.getUserEmail())
							.userRegisteredAt(user.getUserRegisteredAt())
							.userStatusType(user.getUserStatusType())
							.userPermissionType(user.getUserPermissionType())
							.build();
				})
				.orElseThrow(() -> new NotFoundException("User Null."));
	}
}
