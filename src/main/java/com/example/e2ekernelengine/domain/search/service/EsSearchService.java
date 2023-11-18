package com.example.e2ekernelengine.domain.search.service;

import com.example.e2ekernelengine.domain.feed.dto.response.FeedPageableResponse;
import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EsSearchService {

	private final FeedSearchRepository feedSearchRepository;


	public List<FeedPageableResponse> search(String keyword) {
		//		SearchQuery
		//		feedSearchRepository.searchByKeyword(keyword);
		feedSearchRepository.findByBlogTitle(keyword, null);
		return null;
	}
}
