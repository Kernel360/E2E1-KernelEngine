package com.example.e2ekernelengine.user.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.e2ekernelengine.user.dto.request.UserRegisterRequestDto;
import com.example.e2ekernelengine.user.dto.response.UserResponseDto;
import com.example.e2ekernelengine.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public String register(
			@Valid
			Optional<UserRegisterRequestDto> userRegisterRequestDto
	) {
		UserResponseDto userResponseDto = userService.register(userRegisterRequestDto);
		System.out.println(userResponseDto);
		return "redirect:login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	/*@GetMapping("/me")
	@ResponseBody
	public ResponseEntity<UserResponseDto> me() {
		RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
		Long userId = Optional.ofNullable(requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST))
				.map(Object::toString)
				.map(Long::parseLong)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId missing in request."));

		UserResponseDto userResponseDto = userService.getUser(userId);
		return ResponseEntity.ok(userResponseDto);
	}*/

}
