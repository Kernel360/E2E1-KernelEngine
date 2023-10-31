package com.example.e2ekernelengine.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.blog.db.repository.BlogRepository;
import com.example.e2ekernelengine.blog.dto.request.BlogRequestDto;
import com.example.e2ekernelengine.blog.dto.response.BlogResponseDto;
import com.example.e2ekernelengine.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
	private final BlogRepository blogRepository;
	private final BlogJpaRepository blogJpaRepository;

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
	public Long updateCompanyBlogInfo(BlogDataDto blogData) {
		Blog blog = blogJpaRepository.findByBlogRssUrl(blogData.getRssLink())
				.orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));

		Blog updateBlog = Blog.builder().blogId(blog.getBlogId())
				.user(blog.getUser())
				.blogWriterName(blog.getBlogWriterName())
				.blogRssUrl(blog.getBlogRssUrl())
				.blogUrl(blogData.getUrlLink())
				.blogDescription(blogData.getDescription())
				.blogOwnerType(blog.getBlogOwnerType().toString())
				.blogLastBuildAt(blogData.getLastBuildDate())
				.blogLastCrawlAt(blogData.getLastCrawlDate())
				.build();
		blogJpaRepository.save(updateBlog);
		return blog.getBlogId();
	}

	public boolean checkBlogExist(String rssUrl) {
		Blog blog = blogJpaRepository.findByBlogRssUrl(rssUrl).orElse(null);
		if (blog != null)
			return true;
		System.out.println("나ㅓ올나ㅓㅗㅇㄹ");
		return false;
	}

	public Long saveCompanyBlogInfo(BlogDataDto blogData) {
		Blog newBlog = Blog.builder()
				.user(null)
				.blogWriterName(blogData.getTitle())
				.blogRssUrl(blogData.getRssLink())
				.blogUrl(blogData.getUrlLink())
				.blogDescription(blogData.getDescription())
				.blogOwnerType(BlogOwnerType.COMPANY.toString())
				.blogLastBuildAt(blogData.getLastBuildDate())
				.blogLastCrawlAt(blogData.getLastCrawlDate())
				.build();
		blogJpaRepository.save(newBlog);
		return newBlog.getBlogId();

	}
}
