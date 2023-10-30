package com.example.e2ekernelengine.blog.dto.response;

import java.sql.Timestamp;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.util.BlogOwnerType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogResponseDto {
	private Long id;
	private String rss;

	private String url;

	private String description;

	private BlogOwnerType blogOwnerType;

	private Timestamp lastBuildDate;

	public static BlogResponseDto fromEntity(Blog blog) {
		BlogResponseDto response = new BlogResponseDto();
		response.setId(blog.getBlogId());
		response.setRss(blog.getBlogRssUrl());
		response.setUrl(blog.getBlogUrl());
		response.setDescription(blog.getBlogDescription());
		response.setBlogOwnerType(blog.getBlogOwnerType());
		response.setLastBuildDate(blog.getBlogLastBuildAt());
		return response;
	}

}
