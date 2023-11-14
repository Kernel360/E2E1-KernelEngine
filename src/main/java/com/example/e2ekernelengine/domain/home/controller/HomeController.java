package com.example.e2ekernelengine.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	@GetMapping("/main")
	public String getMainPage() {
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

	@GetMapping("/admin")
	public String adminpage() {
		return "/manage/adminPage";
	}

	@GetMapping("/api/v1/manage")
	public String getManagePage() {
		return "/manage/adminPage";
	}

}
