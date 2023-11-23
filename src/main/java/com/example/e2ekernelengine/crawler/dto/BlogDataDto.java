package com.example.e2ekernelengine.crawler.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BlogDataDto {
	private final String title;
	private final String urlLink;
	private final String rssLink;
	private final String description;
	private final Timestamp lastBuildDate;

	@Builder
	public BlogDataDto(String title, String urlLink, String rssLink, String description, Timestamp lastBuildDate) {
		this.title = title;
		this.urlLink = urlLink;
		this.rssLink = rssLink;
		this.lastBuildDate = lastBuildDate;
		this.description = description;
	}

	public static BlogDataDto of(String title, String urlLink, String rssLink, String description,
			Timestamp lastBuildDate) {
		return BlogDataDto.builder()
				.title(title)
				.urlLink(urlLink)
				.rssLink(rssLink)
				.description(description)
				.lastBuildDate(lastBuildDate)
				.build();
	}
}
