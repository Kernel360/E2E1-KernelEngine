package com.example.e2ekernelengine.user.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.user.dto.request.UserLoginRequestDto;
import com.example.e2ekernelengine.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.user.dto.response.UserResponseDto;
import com.example.e2ekernelengine.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/open-api/user")
public class UserOpenApiController {

	private final UserService userService;

	// 사용자 가입 요청
	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(
			@Valid
			@RequestBody Optional<UserRegisterRequestDto> userRegisterRequestDto
	) {
		UserResponseDto userResponseDto = userService.register(userRegisterRequestDto);
		return ResponseEntity.ok(userResponseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponseDto> login(
			@Valid
			@RequestBody Optional<UserLoginRequestDto> userLoginRequestDto
	) {
		UserResponseDto userResponseDto = userService.login(userLoginRequestDto);
		return ResponseEntity.ok(userResponseDto);
	}

}
