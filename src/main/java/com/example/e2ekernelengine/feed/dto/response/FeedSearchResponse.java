package com.example.e2ekernelengine.feed.dto.response;

import static lombok.AccessLevel.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class FeedSearchResponse {
	private final String feedId;
	private final String feedUrl;
	private final String feedTitle;
	private final String feedContent;

}
