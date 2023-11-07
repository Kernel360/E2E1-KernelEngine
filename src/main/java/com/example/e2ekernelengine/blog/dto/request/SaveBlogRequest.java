package com.example.e2ekernelengine.blog.dto.request;

import javax.validation.constraints.NotNull;

import com.example.e2ekernelengine.blog.db.entity.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveBlogRequest {

	private String description;

	private String blogWriterName;

	@NotNull
	private String rss;

	@NotNull
	private String blogOwnerType;

	public Blog toEntity() {
		return Blog.builder()
				.blogDescription(description)
				.blogWriterName(blogWriterName)
				.blogRssUrl(rss)
				.blogOwnerType(blogOwnerType)
				.build();
	}
}
