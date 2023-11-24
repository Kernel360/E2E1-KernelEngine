package com.example.e2ekernelengine.domain.search.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.e2ekernelengine.domain.search.dto.response.EsFeedPageableResponse;
import com.example.e2ekernelengine.domain.search.service.EsSearchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/feeds")
public class EsSearchController {

	private final EsSearchService esSearchService;

	@GetMapping("/search/accurate")
	public String searchFeedBy(
			@RequestParam(value = "q") String keyword,
			@PageableDefault(size = 5) Pageable pageable,
			Model model) {
		Page<EsFeedPageableResponse> feedPage = esSearchService.searchFeedsByKeyword(keyword, pageable);
		prepareModel(model, feedPage, keyword, "accurate");

		return "searchResults";
	}

	private void prepareModel(Model model,
			Page<EsFeedPageableResponse> feedPage,
			String keyword,
			String selectedSortOption) {
		int currentPage = feedPage.getNumber();
		int totalPages = feedPage.getTotalPages();
		int startPage = Math.max(0, currentPage - 2);
		int endPage = Math.min(currentPage + 2, totalPages - 1);

		model.addAttribute("feedPage", feedPage);
		model.addAttribute("query", keyword);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("selectedSortOption", selectedSortOption);
	}

}
