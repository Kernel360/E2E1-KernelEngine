package com.example.e2ekernelengine.domain.feed.controller;

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

	@GetMapping("/search/recent")
	public ModelAndView searchFeedByKeyword(
			@RequestParam(value = "q") String keyword,
			@PageableDefault(size = 5, sort = "feedCreatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		ModelAndView model = new ModelAndView("searchResults");
		Page<FeedPageableResponse> feedPage = feedService.searchFeedsByKeyword(keyword, pageable);
		log.debug("after service logic {}", feedPage);
		int currentPage = feedPage.getNumber();
		int totalPages = feedPage.getTotalPages();
		int startPage = Math.max(0, currentPage - 2);
		int endPage = Math.min(currentPage + 2, totalPages - 1);

		model.addObject("feedPage", feedPage);
		model.addObject("query", keyword);
		model.addObject("startPage", startPage);
		model.addObject("endPage", endPage);
		model.addObject("selectedSortOption", "recent");
		return model;
	}

	@GetMapping("/search/clicked")
	public ModelAndView searchFeedByKeywordOrderByMostVisited(
			@RequestParam(value = "q") String keyword,
			@PageableDefault(size = 5, sort = "feedVisitCount", direction = Sort.Direction.DESC) Pageable pageable) {
		ModelAndView model = new ModelAndView("searchResults");
		Page<FeedPageableResponse> feedPage = feedService.searchFeedsByKeyword(keyword, pageable);
		log.debug("after service logic {}", feedPage);
		int currentPage = feedPage.getNumber();
		int totalPages = feedPage.getTotalPages();
		int startPage = Math.max(0, currentPage - 2);
		int endPage = Math.min(currentPage + 2, totalPages - 1);

		model.addObject("feedPage", feedPage);
		model.addObject("query", keyword);
		model.addObject("startPage", startPage);
		model.addObject("endPage", endPage);
		model.addObject("selectedSortOption", "clicked");
		return model;
	}

	@GetMapping("")
	public ResponseEntity<Page<FeedPageableResponse>> findRecentFeedList(
			@PageableDefault(size = 5, sort = "feedCreatedAt", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<FeedPageableResponse> feedList = feedService.findRecentFeedList(pageable);
		return ResponseEntity.ok(feedList);
	}

	@PostMapping("/visit/{feedId}")
	public void updateDailyVisitCount(@PathVariable Long feedId) {
		feedService.increaseDailyVisitCount(feedId);
	}

}
