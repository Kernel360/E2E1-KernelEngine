package com.example.e2ekernelengine.domain.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.e2ekernelengine.domain.feed.db.entity.FeedDocument;

public interface EsFeedSearchRepository extends ElasticsearchRepository<FeedDocument, String> {

	@Query("{\"bool\": {\"should\": [" +
			"{\"match\": {\"feedTitle\": \"?0\"}}," +
			"{\"match\": {\"feedContent\": \"?0\"}}," +
			"{\"match\": {\"feedDescription\": \"?0\"}}," +
			"{\"match\": {\"blogTitle\": \"?0\"}}" +
			"]}}")
	Page<FeedDocument> searchFeedsByKeyword(String keyword, Pageable pageable);
}
