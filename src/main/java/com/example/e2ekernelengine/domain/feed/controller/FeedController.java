package com.example.e2ekernelengine.domain.feed.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.e2ekernelengine.domain.feed.dto.response.FeedPageableResponse;
import com.example.e2ekernelengine.domain.feed.service.FeedService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
public class FeedController {

	private final FeedService feedService;

	@GetMapping("/search")
	public ModelAndView searchFeedByKeyword(
			@RequestParam(value = "q") String keyword) {
		ModelAndView model = new ModelAndView("searchResults");
		List<FeedPageableResponse> feedList = feedService.searchFeedsByKeyword(keyword);
		log.debug("after service logic {}", feedList);
		model.addObject("feedList", feedList);
		return model;
	}

	@GetMapping("")
	public ResponseEntity<Page<FeedPageableResponse>> findRecentFeedList(
			@PageableDefault(size = 5, sort = "feedCreatedAt", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<FeedPageableResponse> feedList = feedService.findRecentFeedList(pageable);
		return ResponseEntity.ok(feedList);
	}

	@GetMapping("/most-clicked")
	public ResponseEntity<Page<FeedPageableResponse>> findMostClickedFeedList(
			@PageableDefault(size = 5, sort = "visitCount", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<FeedPageableResponse> feedList = feedService.findMostClickedFeedList(pageable);
		return ResponseEntity.ok(feedList);
	}

	@PostMapping("/visit/{feedId}")
	public void updateDailyVisitCount(@PathVariable Long feedId) {
		feedService.increaseDailyVisitCount(feedId);
	}

}
