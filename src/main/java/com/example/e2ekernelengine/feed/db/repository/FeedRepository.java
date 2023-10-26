package com.example.e2ekernelengine.feed.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.feed.db.entity.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

	// 특정 키워드를 포함하는 Feed 검색
	@Query("SELECT f FROM Feed f WHERE LOWER(f.feedTitle) LIKE %:keyword% OR LOWER(f.feedDetail) LIKE %:keyword%")
	List<Feed> searchFeedsByKeyword(@Param("keyword") String keyword);

	// 블로그를 입력받아 Feed 검색 -> 블로그 id로
	List<Feed> searchFeedsByBlog(Blog blog);

}
