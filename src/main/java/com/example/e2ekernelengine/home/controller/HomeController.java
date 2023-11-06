package com.example.e2ekernelengine.home.controller;

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

	@GetMapping("/api/v1/manage")
	public String getManagePage() {
		return "/manage/adminPage";
	}

}
