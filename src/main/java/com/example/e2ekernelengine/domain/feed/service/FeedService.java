package com.example.e2ekernelengine.domain.feed.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.domain.blog.db.entity.Blog;
import com.example.e2ekernelengine.domain.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import com.example.e2ekernelengine.domain.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.domain.feed.dto.response.FeedPageableResponse;
import com.example.e2ekernelengine.global.exception.NotFoundException;

@Service
public class FeedService {
	private final FeedRepository feedRepository;
	private final BlogJpaRepository blogJpaRepository;

	@Autowired
	public FeedService(FeedRepository feedRepository, BlogJpaRepository blogJpaRepository) {
		this.feedRepository = feedRepository;
		this.blogJpaRepository = blogJpaRepository;
	}

	@Transactional
	public List<FeedPageableResponse> searchFeedsByKeyword(String keyword) {

		List<Feed> searchFeedsByKeyword = feedRepository.searchFeedsByKeyword(keyword);
		// List<Long> searchBlogsByBlogWriterName = blogJpaRepository.findBlogIdsByBlogWriterName(keyword);

		return searchFeedsByKeyword.stream()
				.map(FeedPageableResponse::fromEntity)
				.collect(Collectors.toList());

		// for (Long blogId : searchBlogsByBlogWriterName) {
		// 	List<Feed> blogFeeds = feedRepository.searchFeedsByBlog_BlogId(blogId);
		// 	List<FeedSearchResponseDto> blogFeedResponses = blogFeeds.stream()
		// 			.map(feed -> FeedSearchResponseDto
		// 					.create(feed.getFeedId(),
		// 							feed.getFeedUrl(),
		// 							feed.getFeedTitle(),
		// 							feed.getFeedContent()))
		// 			.collect(Collectors.toList());
		// 	feedResponseList.addAll(blogFeedResponses);
		// }
	}

	@Transactional
	public void saveNewFeedsByCrawler(List<FeedDataDto> feedDataList, Long blogId) {

		Blog blog = blogJpaRepository.findById(blogId).orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));
		for (FeedDataDto feedData : feedDataList) {
			Feed feed = Feed.builder()
					.blog(blog)
					.feedUrl(feedData.getLink())
					.feedTitle(feedData.getTitle())
					.feedDescription(feedData.getDescription())
					.feedCreatedAt(feedData.getPubDate())
					.feedContent(feedData.getContent())
					.build();
			feedRepository.save(feed);
		}
	}

	public Page<FeedPageableResponse> findRecentFeedList(Pageable pageable) {
		return feedRepository.findAll(pageable).map(FeedPageableResponse::fromEntity);
	}

}

