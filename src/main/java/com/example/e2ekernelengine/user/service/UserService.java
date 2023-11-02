package com.example.e2ekernelengine.user.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserResponseDto register(@Valid Optional<UserRegisterRequestDto> userRegisterRequestDto) {
		userRegisterRequestDto.orElseThrow(() -> new RegisterException("UserRegisterRequestDto is empty."));

		UserRegisterRequestDto requestDto = userRegisterRequestDto.get();
		checkEmailDuplication(requestDto.getUserEmail());

		String encryptedPassword = bCryptPasswordEncoder.encode(requestDto.getUserPassword());
		User newUser = requestDto.toEntity(encryptedPassword);

		userRepository.save(newUser);
		UserResponseDto userResponseDto = UserResponseDto.fromEntity(newUser);
		return userResponseDto;
	}

	private void checkEmailDuplication(String userEmail) {
		userRepository.findUserByUserEmail(userEmail).ifPresent(user -> {
			throw new RegisterException("This email already exists.");
		});
	}

}

