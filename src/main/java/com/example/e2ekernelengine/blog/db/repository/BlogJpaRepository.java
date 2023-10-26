package com.example.e2ekernelengine.blog.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.blog.db.entity.Blog;

@Repository
public interface BlogJpaRepository extends JpaRepository<Blog, Long> {

	// 블로그 작성자 이름으로 블로그 id 검색
	@Query("SELECT b.blogId FROM Blog b WHERE b.blogWriterName = :blogWriterName")
	List<Long> findBlogIdsByBlogWriterName(@Param("blogWriterName") String blogWriterName);

}
