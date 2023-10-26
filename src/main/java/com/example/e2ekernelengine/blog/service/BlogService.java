package com.example.e2ekernelengine.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.db.repository.BlogRepository;
import com.example.e2ekernelengine.blog.dto.BlogRequestDto;
import com.example.e2ekernelengine.blog.dto.BlogResponseDto;
import com.example.e2ekernelengine.blog.util.BlogOwnerType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
	private final BlogRepository blogRepository;

	@Transactional
	public BlogResponseDto saveBlog(Blog blog) {
		return BlogResponseDto.fromEntity(blogRepository.save(blog));
	}

	public List<BlogResponseDto> findBlogsByOwnerType(BlogOwnerType ownerType) {
		return blogRepository.findByOwnerTypeIsIndividual(ownerType).stream()
				.map(BlogResponseDto::fromEntity)
				.collect(Collectors.toList());
	}

	public BlogResponseDto findBlogById(Long blogId) {
		return BlogResponseDto.fromEntity(blogRepository.findBlogById(blogId));
	}

	public List<BlogResponseDto> findAllBlog() {
		return blogRepository.findAll().stream()
				.map(BlogResponseDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Transactional
	public BlogResponseDto updateBlogById(BlogRequestDto request) {
		Blog updateBlog = request.toEntity();
		blogRepository.save(updateBlog);
		return BlogResponseDto.fromEntity(updateBlog);
	}

	@Transactional
	public BlogResponseDto deleteById(Long blogId) {
		return BlogResponseDto.fromEntity(blogRepository.deleteById(blogId));
	}
}
