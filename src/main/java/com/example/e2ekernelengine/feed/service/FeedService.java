package com.example.e2ekernelengine.feed.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.crawler.FeedData;
import com.example.e2ekernelengine.feed.db.entity.Feed;
import com.example.e2ekernelengine.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.feed.dto.request.FeedSearchRequest;
import com.example.e2ekernelengine.feed.dto.response.FeedSearchResponse;
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

	public List<FeedSearchResponse> searchFeedsByKeyword(FeedSearchRequest request) {
		String keyword = request.getKeyword();

		// FeedRepository를 사용하여 데이터베이스에서 Feed 검색
		List<Feed> searchFeedsByKeyword = feedRepository.searchFeedsByKeyword(keyword);

		// BlogJpaRepository를 사용하여 데이터베이스에서 keyword(이 경우 작성자의 이름)을 이용하여 Blog id 검색
		List<Long> searchBlogsByBlogWriterName = blogJpaRepository.findBlogIdsByBlogWriterName(keyword);

		// searchFeedsByKeyword의 Feed(Entity) -> FeedResponseList(dto)
		List<FeedSearchResponse> feedResponseList = searchFeedsByKeyword.stream()
				.map(feed -> FeedSearchResponse
						.create(feed.getFeedId(),
								feed.getFeedUrl(),
								feed.getFeedTitle(),
								feed.getFeedContent()))
				.collect(Collectors.toList());

		// Blog id로 검색된 모든 Blog에 속하는 Feed(Entity) -> FeedResponseList(dto)
		for (Long blogId : searchBlogsByBlogWriterName) {
			List<Feed> blogFeeds = feedRepository.searchFeedsByBlog_BlogId(blogId);
			List<FeedSearchResponse> blogFeedResponses = blogFeeds.stream()
					.map(feed -> FeedSearchResponse
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
	public void saveNewFeedsByCrawler(List<FeedData> feedDataList, Long blogId) {

		Blog blog = blogJpaRepository.findById(blogId).orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));
		for (FeedData feedData : feedDataList) {
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
}

