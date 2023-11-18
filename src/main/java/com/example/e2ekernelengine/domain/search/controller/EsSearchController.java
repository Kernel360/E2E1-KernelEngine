package com.example.e2ekernelengine.domain.search.controller;

import com.example.e2ekernelengine.domain.feed.dto.response.FeedPageableResponse;
import com.example.e2ekernelengine.domain.search.service.EsSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/search")
public class EsSearchController {

	private final EsSearchService esSearchService;

	@GetMapping()
	public ResponseEntity<List<FeedPageableResponse>> search(@RequestParam(name = "keyword") String keyword) {
		List<FeedPageableResponse> feedList = esSearchService.search(keyword);
		return null;
	}


}
