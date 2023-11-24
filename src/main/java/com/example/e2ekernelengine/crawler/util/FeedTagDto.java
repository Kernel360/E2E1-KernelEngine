package com.example.e2ekernelengine.crawler.util;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedTagDto {
	private final String elementTag;
	private final String pubDateTag;
	private final String linkTag;
	private final String titleTag;
	private final String descriptionTag;
	private final String contentTag;

	@Builder
	public FeedTagDto(String elementTag, String pubDateTag, String linkTag, String titleTag, String descriptionTag,
			String contentTag) {
		this.elementTag = elementTag;
		this.pubDateTag = pubDateTag;
		this.linkTag = linkTag;
		this.titleTag = titleTag;
		this.descriptionTag = descriptionTag;
		this.contentTag = contentTag;
	}

	public static FeedTagDto of(String elementTag, String pubDateTag, String linkTag, String titleTag,
			String descriptionTag, String contentTag) {
		return FeedTagDto.builder()
				.elementTag(elementTag)
				.pubDateTag(pubDateTag)
				.linkTag(linkTag)
				.titleTag(titleTag)
				.descriptionTag(descriptionTag)
				.contentTag(contentTag)
				.build();
	}
}
