package com.example.e2ekernelengine.domain.feed.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String searchFeedByKeyword(
			@RequestParam(value = "q") String keyword,
			@PageableDefault(size = 5, sort = "feedCreatedAt", direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		Page<FeedPageableResponse> feedPage = feedService.searchFeedsByKeyword(keyword, pageable);
		prepareModel(model, feedPage, keyword, "recent");
		return "searchResults";
	}

	@GetMapping("/search/clicked")
	public String searchFeedByKeywordOrderByMostVisited(
			@RequestParam(value = "q") String keyword,
			@PageableDefault(size = 5, sort = "feedVisitCount", direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		Page<FeedPageableResponse> feedPage = feedService.searchFeedsByKeyword(keyword, pageable);
		prepareModel(model, feedPage, keyword, "clicked");
		return "searchResults";
	}

	private void prepareModel(Model model, Page<FeedPageableResponse> feedPage, String keyword,
			String selectedSortOption) {
		int currentPage = feedPage.getNumber();
		int totalPages = feedPage.getTotalPages();
		int startPage = Math.max(0, currentPage - 2);
		int endPage = Math.min(currentPage + 2, totalPages - 1);

		model.addAttribute("feedPage", feedPage);
		model.addAttribute("query", keyword);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("selectedSortOption", selectedSortOption);
	}

	@GetMapping("")
	public ResponseEntity<Page<FeedPageableResponse>> findRecentFeedList(
			@PageableDefault(size = 5, sort = "feedCreatedAt", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<FeedPageableResponse> feedList = feedService.findRecentFeedList(pageable);
		return ResponseEntity.ok(feedList);
	}

	@GetMapping("/clicked")
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
