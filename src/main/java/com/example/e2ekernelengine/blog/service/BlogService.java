package com.example.e2ekernelengine.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.db.repository.BlogJPARepository;
import com.example.e2ekernelengine.blog.db.repository.BlogRepository;
import com.example.e2ekernelengine.blog.dto.BlogRequestDto;
import com.example.e2ekernelengine.blog.dto.BlogResponseDto;
import com.example.e2ekernelengine.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.crawler.BlogData;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
	private final BlogRepository blogRepository;
	private final BlogJPARepository blogJPARepository;

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

	@Transactional
	public Long updateBlogInfo(BlogData blogData) {
		System.out.println("updatebloginfo" + blogData.getRssLink());
		Blog blog = blogJPARepository.findByRss(blogData.getRssLink())
				.orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));
		System.out.println("skjfhsdkjfhskfhskjfh");

		Blog updateBlog = Blog.builder().id(blog.getId())
				.user(blog.getUser())
				.blogWriterName(blog.getBlogWriterName())
				.rss(blog.getRss())
				.url(blogData.getUrlLink())
				.description(blogData.getDescription())
				.ownerType(blog.getOwnerType().toString())
				.lastBuildDate(blogData.getLastBuildDate())
				.lastCrawlDate(blogData.getLastCrawlDate())
				.build();
		blogJPARepository.save(updateBlog);
		return blog.getId();
	}

}
