package com.example.e2ekernelengine.domain.user.service;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.domain.user.db.entity.User;
import com.example.e2ekernelengine.domain.user.db.repository.UserRepository;
import com.example.e2ekernelengine.domain.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.domain.user.dto.response.UserResponseDto;
import com.example.e2ekernelengine.domain.user.exception.RegisterException;
import com.example.e2ekernelengine.domain.user.token.service.TokenService;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenService tokenService;
	@Value("${token.cookie.refresh-name}")
	private String cookieRefreshName;

	@Transactional
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

	@Transactional
	public void leave(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieRefreshName.equals(cookie.getName())) {
					String token = cookie.getValue();
					String userEmail = tokenService.getUserEmail(token);
					User user = userRepository.findUserByUserEmail(userEmail).orElseThrow(
							() -> new NotFoundException("No user found."));

					user.leave();
					logoutAfterLeave(request, response, authentication, cookies);

				}
			}
		}
	}

	public void logoutAfterLeave(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication,
			Cookie[] cookies
	) {
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, null, authentication);
		}

		for (Cookie c : cookies) {
			c.setValue("");
			c.setPath("/");
			c.setMaxAge(0);
			response.addCookie(c);
		}
	}
}

