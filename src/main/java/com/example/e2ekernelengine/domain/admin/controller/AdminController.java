package com.example.e2ekernelengine.domain.admin.controller;

import com.example.e2ekernelengine.domain.admin.dto.response.DailyTop10FeedListResponse;
import com.example.e2ekernelengine.domain.admin.dto.response.RegistrationCountResponse;
import com.example.e2ekernelengine.domain.admin.service.AdminService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

	private final AdminService adminService;

	@GetMapping("feed/top10/daily")
	public DailyTop10FeedListResponse getDailyTop10FeedList(
					@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return adminService.getDailyTop10FeedList(date);
	}

	@GetMapping("registration-count/daily")
	public RegistrationCountResponse getDailyRegistrationCount(
					@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return adminService.getDailyRegistrationCount(date);
	}

	@GetMapping("user-count/jpa")
	public RegistrationCountResponse getTotalUserCountByJpa() {
		return adminService.getTotalUserCountByJpa();
	}

	@GetMapping("user-count/query")
	public RegistrationCountResponse getTotalUserCountByNativeQuery() {
		return adminService.getTotalUserCountByNativeQuery();
	}
}
