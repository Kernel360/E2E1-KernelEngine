package com.example.e2ekernelengine.domain.search.repository;

import com.example.e2ekernelengine.domain.feed.db.entity.FeedDocument;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CustomFeedSearchRepository {

	List<FeedDocument> searchByKeyword(String keyword, Pageable pageable);
}
