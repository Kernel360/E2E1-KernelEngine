package com.example.e2ekernelengine.domain.admin.dto.response;

import com.example.e2ekernelengine.domain.admin.dto.MostVisitedFeedDto;
import java.util.List;
import lombok.Getter;

@Getter
public class DailyTop10FeedListResponse {

	List<MostVisitedFeedDto> dailyTop10FeedList;

	public DailyTop10FeedListResponse(List<MostVisitedFeedDto> dailyTop10FeedList) {
		this.dailyTop10FeedList = dailyTop10FeedList;
	}

	public static DailyTop10FeedListResponse from(List<MostVisitedFeedDto> dailyTop10FeedList) {
		return new DailyTop10FeedListResponse(dailyTop10FeedList);
	}
}
