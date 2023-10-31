package com.example.e2ekernelengine.user.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.global.exception.NotFoundException;
import com.example.e2ekernelengine.user.converter.UserConverter;
import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.db.repository.UserRepository;
import com.example.e2ekernelengine.user.dto.request.UserLoginRequestDto;
import com.example.e2ekernelengine.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.user.dto.response.UserResponseDto;
import com.example.e2ekernelengine.user.exception.LoginException;
import com.example.e2ekernelengine.user.exception.RegisterException;
import com.example.e2ekernelengine.user.util.UserStatusType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserConverter userConverter;

	public UserResponseDto register(@Valid Optional<UserRegisterRequestDto> userRegisterRequestDto) {

		userRegisterRequestDto.orElseThrow(() -> new RegisterException("UserRegisterRequestDto is empty."));

		// 이메일 중복 체크
		UserRegisterRequestDto requestDto = userRegisterRequestDto.get();
		userRepository.findUserByUserEmail(requestDto.getUserEmail()).ifPresent(
				registerUser -> {
					throw new RegisterException("This email already exists.");
				}
		);

		// 회원 가입
		User newUser = userConverter.toEntity(userRegisterRequestDto);
		userRepository.save(newUser);
		UserResponseDto userResponseDto = userConverter.toDto(newUser);
		return userResponseDto;
	}

	public UserResponseDto login(@Valid Optional<UserLoginRequestDto> userLoginRequestDto) {

		userLoginRequestDto.orElseThrow(() -> new LoginException("UserLoginRequestDto is empty."));

		String userEmail = userLoginRequestDto.get().getUserEmail();
		String userPassword = userLoginRequestDto.get().getUserPassword();
		User loginUser = getUserIfExists(userEmail, userPassword);
		UserResponseDto userResponseDto = userConverter.toDto(loginUser);
		return userResponseDto;
		/*var userEntity = userService.login(request.getEmail(), request.getPassword());
		// 사용자 없으면 throw
		// TODO 토큰 생성 로직으로 변경하기
		return userConverter.toDto(userEntity);*/
	}

	public User getUserIfExists(String userEmail, String userPassword) {
		return userRepository.findFirstByUserEmailAndUserPasswordAndUserStatusTypeOrderByUserIdDesc(
				userEmail,
				userPassword,
				UserStatusType.ACTIVE
		).orElseThrow(() -> new NotFoundException("User not found."));
	}

	/*public User me(Long userId) {
		return userRepository.findFirstByUserIdAndUserStatusTypeOrderByUserIdDesc(
		)
	}*/
}

