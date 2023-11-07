package com.example.e2ekernelengine.domain.user.controller;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.e2ekernelengine.domain.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.domain.user.dto.response.UserResponseDto;
import com.example.e2ekernelengine.domain.user.service.UserService;
import com.example.e2ekernelengine.domain.user.token.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	private final TokenService tokenService;
	@Value("${token.secret.key}")
	private String secretKey;
	@Value("${token.cookie.refresh-name}")
	private String cookieRefreshName;

	@PostMapping("/signup")
	public String register(
			@Valid
			Optional<UserRegisterRequestDto> userRegisterRequestDto
	) {
		UserResponseDto userResponseDto = userService.register(userRegisterRequestDto);
		System.out.println(userResponseDto);
		return "redirect:login";
	}

	@GetMapping("/index")
	public String mainpage() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/mypage")
	public String mypages() {
		return "mypage";
	}

	@PostMapping("/leave")
	public String leave(
			HttpServletRequest request
	) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieRefreshName.equals(cookie.getName())) {
					String token = cookie.getValue();
					String userEmail = tokenService.getUserEmail(token);
					userService.leave(userEmail);
				}
			}
		}

		return "redirect:index";
	}
}
