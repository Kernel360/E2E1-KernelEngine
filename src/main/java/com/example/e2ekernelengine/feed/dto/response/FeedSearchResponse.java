package com.example.e2ekernelengine.feed.dto.response;

import static lombok.AccessLevel.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class FeedSearchResponse {
	private final Long feedId;
	private final String feedUrl;
	private final String feedTitle;
	private final String feedContent;
	
	public static FeedSearchResponse create(Long feedId, String feedUrl, String feedTitle, String feedContent) {
		return new FeedSearchResponse(feedId, feedUrl, feedTitle, feedContent);
	}

}
