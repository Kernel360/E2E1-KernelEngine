package com.example.e2ekernelengine.domain.admin.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DateAndTotalUserCountDto {

	private final LocalDate date;

	private final int totalRegistrationCount;

	@Builder
	public DateAndTotalUserCountDto(LocalDate date, int totalRegistrationCount) {
		this.date = date;
		this.totalRegistrationCount = totalRegistrationCount;
	}

	public static DateAndTotalUserCountDto of(LocalDate date, int totalRegistrationCount) {
		return DateAndTotalUserCountDto.builder()
						.date(date)
						.totalRegistrationCount(totalRegistrationCount)
						.build();
	}
}
