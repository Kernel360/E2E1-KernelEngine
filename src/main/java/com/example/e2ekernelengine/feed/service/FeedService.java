package com.example.e2ekernelengine.feed.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.feed.db.entity.Feed;
import com.example.e2ekernelengine.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.feed.dto.response.FeedSearchResponseDto;

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
	public List<FeedSearchResponseDto> searchFeedsByKeyword(String keyword) {

		List<Feed> searchFeedsByKeyword = feedRepository.searchFeedsByKeyword(keyword);

		List<Long> searchBlogsByBlogWriterName = blogJpaRepository.findBlogIdsByBlogWriterName(keyword);

		List<FeedSearchResponseDto> feedResponseList = searchFeedsByKeyword.stream()
				.map(feed -> FeedSearchResponseDto
						.create(feed.getFeedId(),
								feed.getFeedUrl(),
								feed.getFeedTitle(),
								feed.getFeedContent()))
				.collect(Collectors.toList());

		for (Long blogId : searchBlogsByBlogWriterName) {
			List<Feed> blogFeeds = feedRepository.searchFeedsByBlog_BlogId(blogId);
			List<FeedSearchResponseDto> blogFeedResponses = blogFeeds.stream()
					.map(feed -> FeedSearchResponseDto
							.create(feed.getFeedId(),
									feed.getFeedUrl(),
									feed.getFeedTitle(),
									feed.getFeedContent()))
					.collect(Collectors.toList());
			feedResponseList.addAll(blogFeedResponses);
		}

		return feedResponseList;
	}

}

