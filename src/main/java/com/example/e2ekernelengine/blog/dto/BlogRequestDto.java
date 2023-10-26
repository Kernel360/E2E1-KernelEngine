package com.example.e2ekernelengine.blog.dto;

import com.example.e2ekernelengine.blog.db.entity.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequestDto {
	private Long id;
	private String rss;
	private String url;
	private String description;
	private String ownerType;

	public Blog toEntity() {
		return Blog.builder()
				.id(id)
				.rss(rss)
				.url(url)
				.description(description)
				.ownerType(ownerType)
				.build();
	}
}
