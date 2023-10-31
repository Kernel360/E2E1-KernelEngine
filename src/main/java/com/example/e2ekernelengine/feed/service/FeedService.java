package com.example.e2ekernelengine.feed.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.feed.db.entity.Feed;
import com.example.e2ekernelengine.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.feed.dto.response.FeedSearchResponseDto;
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

	public Page<FeedSearchResponseDto> findRecentFeedList(Pageable pageable) {
		return feedRepository.findAll(pageable).map(FeedSearchResponseDto::fromEntity);
	}

	// 이렇게 쓸 수도 있음
	// public Page<Feed> findAll() {
	// 	Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "feedCreatedAt");
	// 	return feedRepository.findAll(pageable);
	// }

}

