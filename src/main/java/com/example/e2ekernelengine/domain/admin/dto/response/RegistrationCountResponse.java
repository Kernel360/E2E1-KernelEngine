package com.example.e2ekernelengine.domain.admin.dto.response;

import com.example.e2ekernelengine.domain.admin.dto.DateAndTotalUserCountDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegistrationCountResponse {

	private final List<DateAndTotalUserCountDto> thisWeekTotalUserCountList;

	private final List<DateAndTotalUserCountDto> lastWeekTotalUserCountList;

	@Builder
	public RegistrationCountResponse(List<DateAndTotalUserCountDto> thisWeekTotalUserCountList,
					List<DateAndTotalUserCountDto> lastWeekTotalUserCountList) {
		this.thisWeekTotalUserCountList = thisWeekTotalUserCountList;
		this.lastWeekTotalUserCountList = lastWeekTotalUserCountList;
	}

	public static RegistrationCountResponse of(List<DateAndTotalUserCountDto> thisWeekTotalUserCountList,
					List<DateAndTotalUserCountDto> lastWeekTotalUserCountList) {
		return RegistrationCountResponse.builder()
						.thisWeekTotalUserCountList(thisWeekTotalUserCountList)
						.lastWeekTotalUserCountList(lastWeekTotalUserCountList)
						.build();
	}
}
