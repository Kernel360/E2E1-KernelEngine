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
import com.example.e2ekernelengine.global.exception.ConflictException;
import com.example.e2ekernelengine.global.exception.NotFoundException;

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
