package com.example.e2ekernelengine.crawler;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.ToString;

@ToString
public class BlogData {
	private final String title;
	private final String link;
	private final String description;
	private final String lastBuildDate;
	private final Timestamp lastCrawlDate;

	@Builder
	public BlogData(String title, String link, String description, String lastBuildDate, String content,
			Timestamp lastCrawlDate) {
		this.title = title;
		this.link = link;
		this.lastBuildDate = lastBuildDate;
		this.description = description;
		this.lastCrawlDate = lastCrawlDate;
	}
}
