package com.example.e2ekernelengine.feed.dto.response;

import static lombok.AccessLevel.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class FeedSearchResponse {
	private final String feedId;
	private final String feedUrl;
	private final String feedTitle;
	private final String feedContent;

	// 정적 팩토리 메서드를 사용하여 객체 생성
	public static FeedSearchResponse create(String feedId, String feedUrl, String feedTitle, String feedContent) {
		return new FeedSearchResponse(feedId, feedUrl, feedTitle, feedContent);
	}

}
