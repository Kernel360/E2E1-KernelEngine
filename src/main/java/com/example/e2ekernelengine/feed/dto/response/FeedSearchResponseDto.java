package com.example.e2ekernelengine.feed.dto.response;

import static lombok.AccessLevel.*;

import com.example.e2ekernelengine.feed.db.entity.Feed;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class FeedSearchResponseDto {
	private final Long feedId;
	private final String feedUrl;
	private final String feedTitle;
	private final String feedContent;

	public static FeedSearchResponseDto create(Long feedId, String feedUrl, String feedTitle, String feedContent) {
		return new FeedSearchResponseDto(feedId, feedUrl, feedTitle, feedContent);
	}

	public static FeedSearchResponseDto fromEntity(Feed feed) {
		return FeedSearchResponseDto.builder()
				.feedId(feed.getFeedId())
				.feedUrl(feed.getFeedUrl())
				.feedContent(feed.getFeedContent())
				.feedTitle(feed.getFeedTitle())
				.build();
	}
}
