package com.example.e2ekernelengine.domain.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.domain.blog.db.entity.Blog;
import com.example.e2ekernelengine.domain.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.domain.blog.db.repository.BlogRepository;
import com.example.e2ekernelengine.domain.blog.dto.request.BlogRequestDto;
import com.example.e2ekernelengine.domain.blog.dto.response.BlogResponseDto;
import com.example.e2ekernelengine.domain.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.global.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
	private final BlogRepository blogRepository;
	private final BlogJpaRepository blogJpaRepository;

	// ComPany or Individual에 대한 로직 추가
	// TODO: 등록하려는 블로그가 유효한 블로그인지 확인하는 로직 추가
	// TODO: 블로그 등록시 블로그 데이터도 같이 저장하도록 로직 추가
	@Transactional
	public BlogResponseDto saveBlog(Blog blog) {
		if (!blogJpaRepository.findByBlogRssUrl(blog.getBlogRssUrl()).isEmpty()) {
			throw new ConflictException("이미 등록된 블로그입니다.");
		}
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
