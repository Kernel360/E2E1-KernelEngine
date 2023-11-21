package com.example.e2ekernelengine.domain.search.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.domain.search.dto.response.EsFeedPageableResponse;
import com.example.e2ekernelengine.domain.search.service.EsSearchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/search")
public class EsSearchController {

	private final EsSearchService esSearchService;
	
	@GetMapping()
	public ResponseEntity<Page<EsFeedPageableResponse>> search(
			@RequestParam(value = "q") String keyword,
			Pageable pageable) {
		Page<EsFeedPageableResponse> feedPage = esSearchService.searchFeedsByKeyword(keyword, pageable);
		if (feedPage.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(feedPage);
	}

}
