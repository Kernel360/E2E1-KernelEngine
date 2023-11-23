package com.example.e2ekernelengine.crawler.util;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BlogTagDto {
	private final String typeTag;
	private final String titleTag;
	private final String linkTag;
	private final String descriptionTag;
	private final String lastBuildDateTag;

	@Builder
	public BlogTagDto(String typeTag, String titleTag, String linkTag, String descriptionTag, String lastBuildDateTag) {
		this.typeTag = typeTag;
		this.titleTag = titleTag;
		this.linkTag = linkTag;
		this.descriptionTag = descriptionTag;
		this.lastBuildDateTag = lastBuildDateTag;
	}

	public static BlogTagDto of(String typeTag, String titleTag, String linkTag, String descriptionTag,
			String lastBuildDateTag) {
		return BlogTagDto.builder()
				.typeTag(typeTag)
				.titleTag(titleTag)
				.linkTag(linkTag)
				.descriptionTag(descriptionTag)
				.lastBuildDateTag(lastBuildDateTag)
				.build();
	}
}
