package com.example.e2ekernelengine.domain.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.domain.blog.db.entity.Blog;
import com.example.e2ekernelengine.domain.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.domain.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.domain.user.db.entity.User;
import com.example.e2ekernelengine.global.exception.ConflictException;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogCrawlerService {

	private final BlogJpaRepository blogJpaRepository;

	// @Transactional
	// public void saveUserBlog(Long userId, SaveUserBlogRequest request) {
	// 	blogJpaRepository.findByBlogRssUrl(request.getBlogRssUrl()).ifPresent(blog -> {
	// 		throw new ConflictException("이미 존재하는 블로그 입니다.");
	// 	});
	//
	// 	// rss url로 블로그 정보를 가져온다.
	//
	// 	// save IndividualBlog info로 저장하기
	//
	// }

	public boolean checkBlogExist(String rssUrl) {
		Blog blog = blogJpaRepository.findByBlogRssUrl(rssUrl).orElse(null);
		return blog != null;
	}

	public void isExistBlogThrowException(String blogRssUrl) {
		blogJpaRepository.findByBlogRssUrl(blogRssUrl).ifPresent(blog -> {
			throw new ConflictException("이미 존재하는 블로그 입니다.");
		});
	}

	@Transactional
	public Blog saveBlogInfo(User user, BlogDataDto blogData, BlogOwnerType blogOwnerType) {
		Blog newBlog = Blog.of(user, blogData.getTitle(), blogData.getTitle(), blogData.getRssLink(), blogData.getUrlLink(),
				blogData.getDescription(), blogOwnerType, blogData.getLastBuildDate(), null);
		blogJpaRepository.save(newBlog);
		return newBlog;
	}

	@Transactional
	public Blog updateBlogInfo(BlogDataDto blogData, BlogOwnerType blogOwnerType) {
		Blog blog = blogJpaRepository.findByBlogRssUrl(blogData.getRssLink())
				.orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));

		Blog updateBlog = Blog.of(blog.getBlogId(), blog.getUser(), blog.getBlogTitle(), blog.getBlogWriterName(),
				blog.getBlogRssUrl(), blogData.getUrlLink(), blogData.getDescription(), blogOwnerType,
				blogData.getLastBuildDate(), blog.getBlogLastCrawlAt());
		blogJpaRepository.save(updateBlog);
		return blog;
	}
}
