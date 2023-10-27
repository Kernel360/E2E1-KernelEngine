package com.example.e2ekernelengine.feed.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.feed.db.entity.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
	
	@Query("SELECT f FROM Feed f WHERE LOWER(f.feedTitle) LIKE %:keyword% OR LOWER(f.feedContent) LIKE %:keyword%")
	List<Feed> searchFeedsByKeyword(@Param("keyword") String keyword);

	List<Feed> searchFeedsByBlog_BlogId(Long blogId);

}
