package com.example.e2ekernelengine.domain.admin.dto;

import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MostVisitedFeedDto {

	private final String feedTitle;

	private final String feedUrl;

	private final String blogTitle;

	private final String blogWriterName;

	private final String publishedDate;

	private final Integer feedVisitCount;

	@Builder
	public MostVisitedFeedDto(String feedTitle, String feedUrl, String blogTitle, String blogWriterName,
					String publishedDate, Integer feedVisitCount) {
		this.feedTitle = feedTitle;
		this.feedUrl = feedUrl;
		this.blogTitle = blogTitle;
		this.blogWriterName = blogWriterName;
		this.publishedDate = publishedDate;
		this.feedVisitCount = feedVisitCount;
	}

	public static MostVisitedFeedDto from(Feed feed) {
		return MostVisitedFeedDto.builder()
						.feedTitle(feed.getFeedTitle())
						.feedUrl(feed.getFeedUrl())
						.blogTitle(feed.getBlog().getBlogDescription())
						.blogWriterName(feed.getBlog().getBlogWriterName())
						.publishedDate(feed.getFeedCreatedAt().toString())
						.feedVisitCount(feed.getFeedVisitCount())
						.build();
	}

	public MostVisitedFeedDto of(String feedTitle, String feedUrl, String blogTitle, String blogWriterName,
					String publishedDate, Integer feedVisitCount) {
		return MostVisitedFeedDto.builder()
						.feedTitle(feedTitle)
						.feedUrl(feedUrl)
						.blogTitle(blogTitle)
						.blogWriterName(blogWriterName)
						.publishedDate(publishedDate)
						.feedVisitCount(feedVisitCount)
						.build();
	}
}
