package com.example.e2ekernelengine.domain.feed.db.entity;

import java.sql.Timestamp;
import javax.persistence.Id;
import lombok.Builder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "feed")
@Mapping(mappingPath = "elasticsearch/feed/feed-mapping.json")
@Setting(settingPath = "elasticsearch/feed/feed-setting.json")
public class FeedDocument {

	//	private Blog blog;
	private final String blogTitle;

	private final String feedUrl;

	private final String feedTitle;

	private final String feedContent;

	private final Timestamp feedCreatedAt;

	private final String feedDescription;

	private final Integer feedVisitCount;

	@Id
	private final Long id;

	@Builder
	public FeedDocument(String blogTitle, String feedUrl, String feedTitle, String feedContent, Timestamp feedCreatedAt,
					String feedDescription, Integer feedVisitCount, Long feedId) {
		this.blogTitle = blogTitle;
		this.feedUrl = feedUrl;
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.feedCreatedAt = feedCreatedAt;
		this.feedDescription = feedDescription;
		this.feedVisitCount = feedVisitCount;
		this.id = feedId;
	}
}
