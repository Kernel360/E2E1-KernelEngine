package com.example.e2ekernelengine.domain.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.domain.feed.db.entity.FeedDocument;
import com.example.e2ekernelengine.domain.search.dto.response.EsFeedPageableResponse;
import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EsSearchService {

	private final FeedSearchRepository feedSearchRepository;


	/*public List<FeedPageableResponse> search(String keyword) {
		//		SearchQuery
		//		feedSearchRepository.searchByKeyword(keyword);
		feedSearchRepository.findByBlogTitle(keyword, null);
		return null;
	}*/

	public Page<EsFeedPageableResponse> searchFeedsByKeyword(String keyword, Pageable pageable) {

		Page<FeedDocument> page = feedSearchRepository.searchFeedsByKeyword(
				keyword, pageable);
		return page.map(EsFeedPageableResponse::fromDocument);

	}
}
