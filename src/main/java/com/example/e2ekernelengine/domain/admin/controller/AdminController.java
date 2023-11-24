package com.example.e2ekernelengine.domain.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.domain.admin.dto.response.UserCountResponse;
import com.example.e2ekernelengine.domain.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

	private final AdminService adminService;

	@GetMapping("user-count/jpa")
	public UserCountResponse getTotalUserCountByJpa() {
		return adminService.getTotalUserCountByJpa();
	}

	@GetMapping("user-count/query")
	public UserCountResponse getTotalUserCountByNativeQuery() {
		return adminService.getTotalUserCountByNativeQuery();
	}
}
