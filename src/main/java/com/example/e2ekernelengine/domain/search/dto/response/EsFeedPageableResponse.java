package com.example.e2ekernelengine.domain.search.dto.response;

import com.example.e2ekernelengine.domain.feed.db.entity.FeedDocument;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EsFeedPageableResponse {
	private final Long feedId;
	private final String feedUrl;
	private final String feedTitle;
	private final String feedDescription;
	private final String feedCreatedAt;
	private final String blogWriterName;

	public static EsFeedPageableResponse fromDocument(FeedDocument feedDocument) {
		return EsFeedPageableResponse.builder()
				.feedId(feedDocument.getId())
				.feedUrl(feedDocument.getFeedUrl())
				.feedTitle(feedDocument.getFeedTitle())
				.feedDescription(feedDocument.getFeedDescription())
				.feedCreatedAt(feedDocument.getFeedCreatedAt().toString())
				.blogWriterName(feedDocument.getBlogTitle())
				.build();
	}
}
