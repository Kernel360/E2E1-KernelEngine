package com.example.e2ekernelengine.domain.feed.db.entity;

import java.sql.Timestamp;
import javax.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "feed")
@Mapping(mappingPath = "elasticsearch/feed/feed-mapping.json")
@Setting(settingPath = "elasticsearch/feed/feed-setting.json")
public class FeedDocument {

	@Id
	private String id;

	private Long feedId;

	//	private Blog blog;
	private String blogTitle;

	private String feedUrl;

	private String feedTitle;

	private String feedContent;

	private Timestamp feedCreatedAt;

	private String feedDescription;

	private Integer visitCount;
}
