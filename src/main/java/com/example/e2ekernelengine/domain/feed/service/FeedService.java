package com.example.e2ekernelengine.domain.feed.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import com.example.e2ekernelengine.domain.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.domain.feed.dto.response.FeedPageableResponse;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedService {

	private final FeedRepository feedRepository;

	@Transactional
	public Page<FeedPageableResponse> searchFeedsByKeyword(String keyword, Pageable pageable) {
		Page<Feed> page = feedRepository.searchFeedsByKeyword(keyword, pageable);
		return page.map(FeedPageableResponse::fromEntity);
	}

	public Page<FeedPageableResponse> findRecentFeedList(Pageable pageable) {
		return feedRepository.findAll(pageable).map(FeedPageableResponse::fromEntity);
	}

	public Page<FeedPageableResponse> findMostClickedFeedList(Pageable pageable) {
		return feedRepository.findAll(pageable).map(FeedPageableResponse::fromEntity);
	}

	@Transactional
	public void increaseDailyVisitCount(Long feedId) {
		Feed feed = feedRepository.findById(feedId)
				.orElseThrow(() -> new NotFoundException("해당 피드가 존재하지 않습니다"));
		feed.increaseVisitCount();
		feedRepository.save(feed);
	}

}

