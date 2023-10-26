package com.example.e2ekernelengine.blog.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e2ekernelengine.blog.db.entity.Blog;

public interface BlogJPARepository extends JpaRepository<Blog, Long> {

	Optional<Blog> findByUrl(String link);

	Optional<Blog> findByRss(String link);
}
