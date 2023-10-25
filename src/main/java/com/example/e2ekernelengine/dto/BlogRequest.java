package com.example.e2ekernelengine.dto;

import com.example.e2ekernelengine.entity.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {
	private String rss;
	private String url;
	private String description;
	private String ownerType;

	public Blog toEntity() {
		return Blog.builder()
				.rss(rss)
				.url(url)
				.description(description)
				.ownerType(ownerType)
				.build();
	}
}
