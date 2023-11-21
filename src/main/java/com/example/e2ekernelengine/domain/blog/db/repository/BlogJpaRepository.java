package com.example.e2ekernelengine.domain.blog.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.domain.blog.db.entity.Blog;

@Repository
public interface BlogJpaRepository extends JpaRepository<Blog, Long> {

	Optional<Blog> findByBlogRssUrl(String rssLink);
}
