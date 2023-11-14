package com.example.e2ekernelengine.domain.feed.dto.response;

import com.example.e2ekernelengine.domain.feed.db.entity.Feed;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedPageableResponse {
	private final Long feedId;
	private final String feedUrl;
	private final String feedTitle;
	private final String feedDescription;
	private final String feedCreatedAt;
	private final String blogWriterName;

	@Builder
	public FeedPageableResponse(Long feedId, String feedUrl, String feedTitle, String feedDescription,
			String feedCreatedAt, String blogWriterName) {
		this.feedId = feedId;
		this.feedUrl = feedUrl;
		this.feedTitle = feedTitle;
		this.feedDescription = feedDescription;
		this.feedCreatedAt = feedCreatedAt;
		this.blogWriterName = blogWriterName;
	}

	public static FeedPageableResponse fromEntity(Feed feed) {
		return FeedPageableResponse.builder()
				.feedId(feed.getFeedId())
				.feedUrl(feed.getFeedUrl())
				.feedTitle(feed.getFeedTitle())
				.feedDescription(feed.getFeedDescription())
				.feedCreatedAt(feed.getFeedCreatedAt().toString())
				.blogWriterName(feed.getBlog().getBlogWriterName())
				.build();
	}
}
