package com.example.e2ekernelengine.domain.search.repository;

import com.example.e2ekernelengine.domain.feed.db.entity.FeedDocument;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FeedSearchRepository extends ElasticsearchRepository<FeedDocument, String> {

	List<FeedDocument> findByBlogTitle(String blogTitle, Pageable pageable);
}
