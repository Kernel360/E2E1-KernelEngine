package com.example.e2ekernelengine.domain.search.repository;

import com.example.e2ekernelengine.domain.feed.db.entity.FeedDocument;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FeedSearchRepositoryImpl implements CustomFeedSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public List<FeedDocument> searchByKeyword(String keyword, Pageable pageable) {
		System.out.println("FeedSearchRepositoryImpl.searchByKeyword");
		return null;
	}
}
