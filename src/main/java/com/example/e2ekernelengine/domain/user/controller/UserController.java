package com.example.e2ekernelengine.domain.user.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.e2ekernelengine.domain.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.domain.user.dto.response.UserResponseDto;
import com.example.e2ekernelengine.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger("USER_ACCOUNT_LOG");
	private final UserService userService;

	@PostMapping("/signup")
	public String register(
			@Valid
					Optional<UserRegisterRequestDto> userRegisterRequestDto
	) {
		log.info("register request | {}",
				userRegisterRequestDto.get().getUserName() + " " + userRegisterRequestDto.get().getUserEmail() + " "
						+ userRegisterRequestDto.get().getUserPassword());
		UserResponseDto userResponseDto = userService.register(userRegisterRequestDto);
		// System.out.println(userResponseDto);
		log.info("request finished");
		return "redirect:login";
	}


	@PostMapping("/deleteAccount")
	public String deleteAccount(
			HttpServletRequest request,
			HttpServletResponse response
	) {
		userService.deleteAccount(request, response);
		return "redirect:/";
	}
}
