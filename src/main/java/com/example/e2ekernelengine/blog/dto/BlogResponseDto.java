package com.example.e2ekernelengine.blog.dto;

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

	private BlogOwnerType ownerType;

	private Timestamp lastBuildDate;

	public static BlogResponseDto fromEntity(Blog blog) {
		BlogResponseDto response = new BlogResponseDto();
		response.setId(blog.getId());
		response.setRss(blog.getRss());
		response.setUrl(blog.getUrl());
		response.setDescription(blog.getDescription());
		response.setOwnerType(blog.getOwnerType());
		response.setLastBuildDate(blog.getLastBuildDate());
		return response;
	}

}
