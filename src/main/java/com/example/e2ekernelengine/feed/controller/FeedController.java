package com.example.e2ekernelengine.feed.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.e2ekernelengine.feed.dto.response.FeedSearchResponseDto;
import com.example.e2ekernelengine.feed.service.FeedService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
public class FeedController {

	private final FeedService feedService;

	//-- Read(Search)--//
	@ResponseBody
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<FeedSearchResponseDto>> searchFeedByKeyword(
			@PathVariable String keyword) {

		List<FeedSearchResponseDto> feedResponses = feedService.searchFeedsByKeyword(keyword);

		if (feedResponses.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(feedResponses);
		}
	}
}
