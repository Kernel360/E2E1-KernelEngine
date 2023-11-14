package com.example.e2ekernelengine.domain.blog.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.domain.blog.db.entity.Blog;

@Repository
public interface BlogJpaRepository extends JpaRepository<Blog, Long> {

	@Query("SELECT b.blogId FROM Blog b WHERE b.blogWriterName = :blogWriterName")
	List<Long> findBlogIdsByBlogWriterName(@Param("blogWriterName") String blogWriterName);

	Optional<Blog> findByBlogRssUrl(String rssLink);
}
