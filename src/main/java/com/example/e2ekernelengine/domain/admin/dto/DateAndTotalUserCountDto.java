package com.example.e2ekernelengine.domain.admin.dto;

import java.math.BigInteger;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DateAndTotalUserCountDto {
	private final LocalDate date;
	private final BigInteger totalUserCount;

	@Builder
	public DateAndTotalUserCountDto(LocalDate date, BigInteger totalUserCount) {
		this.date = date;
		this.totalUserCount = totalUserCount;
	}
}
