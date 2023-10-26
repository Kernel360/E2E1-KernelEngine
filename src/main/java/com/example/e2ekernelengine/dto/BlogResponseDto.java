package com.example.e2ekernelengine.dto;

import java.sql.Timestamp;

import com.example.e2ekernelengine.entity.Blog;
import com.example.e2ekernelengine.entity.OwnerType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogResponseDto {
	private Long id;
	private String rss;

	private String url;

	private String description;

	private OwnerType ownerType;

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
