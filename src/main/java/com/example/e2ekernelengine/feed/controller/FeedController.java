package com.example.e2ekernelengine.feed.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.e2ekernelengine.feed.dto.request.FeedSearchRequest;
import com.example.e2ekernelengine.feed.dto.response.FeedSearchResponse;
import com.example.e2ekernelengine.feed.service.FeedService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
public class FeedController {

	private final FeedService feedService;

	//-- Read(Search)--//
	@ResponseBody
	@GetMapping("/search")
	public ResponseEntity<List<FeedSearchResponse>> searchFeedByKeyword(
			@RequestBody FeedSearchRequest feedSearchRequest) {

		// 키워드를 담은 feedSearchRequest를 통째로 보내서 Feed 데이터를 검색
		// 이유: 최대한 많은 로직을 service에서 처리하기 위함
		List<FeedSearchResponse> feedResponses = feedService.searchFeedsByKeyword(feedSearchRequest);

		if (feedResponses.isEmpty()) {
			// 검색 결과가 없는 경우 404 Not Found 응답
			return ResponseEntity.notFound().build();
		} else {
			// 검색 결과가 있는 경우 200 OK 응답과 JSON 데이터 반환
			return ResponseEntity.ok(feedResponses);
		}
	}
}
