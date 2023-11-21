package com.example.e2ekernelengine.domain.feed.db.entity;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.Builder;
import lombok.Getter;

@Document(indexName = "feed")
@Mapping(mappingPath = "elasticsearch/feed/feed-mapping.json")
@Setting(settingPath = "elasticsearch/feed/feed-setting.json")
@Getter
public class FeedDocument {

	//	private Blog blog;
	private final String blogTitle;

	private final String feedUrl;

	private final String feedTitle;

	private final String feedContent;

	private final Long feedCreatedAt;

	private final String feedDescription;

	private final Integer feedVisitCount;

	@Id
	private final Long id;

	@Builder
	public FeedDocument(String blogTitle, String feedUrl, String feedTitle, String feedContent, Long feedCreatedAt,
			String feedDescription, Integer feedVisitCount, Long id) {
		this.blogTitle = blogTitle;
		this.feedUrl = feedUrl;
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.feedCreatedAt = feedCreatedAt;
		this.feedDescription = feedDescription;
		this.feedVisitCount = feedVisitCount;
		this.id = id;
	}
}
