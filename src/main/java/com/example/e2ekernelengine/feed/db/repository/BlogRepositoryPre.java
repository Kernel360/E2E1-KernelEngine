package com.example.e2ekernelengine.feed.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.blog.db.entity.Blog;

@Repository
public interface BlogRepositoryPre extends JpaRepository<Blog, Long> {

	// user_id를 사용하여 Blog 검색
	List<Blog> findBlogsByBlogWriterName(Long blogWriterName);

}
