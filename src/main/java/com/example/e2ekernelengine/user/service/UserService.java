package com.example.e2ekernelengine.user.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.user.converter.UserConverter;
import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.db.repository.UserRepository;
import com.example.e2ekernelengine.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.user.dto.response.UserResponseDto;
import com.example.e2ekernelengine.user.exception.RegisterException;

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

}

