package com.example.e2ekernelengine.domain.feed.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.domain.feed.db.entity.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

	@Query("select f from Feed f where lower(f.feedTitle) like lower(concat('%', :keyword, '%')) "
			+ "or lower(f.feedContent) like lower(concat('%', :keyword, '%')) "
			+ "or lower(f.feedDescription) like lower(concat('%', :keyword, '%'))"
			+ "or lower(f.blog.blogWriterName) like lower(concat('%', :keyword, '%')) ")
	Page<Feed> searchFeedsByKeyword(@Param("keyword") String keyword, Pageable request);

	Page<Feed> findAll(Pageable request);

}
