package com.example.e2ekernelengine.domain.feed.service;

import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.domain.blog.db.entity.Blog;
import com.example.e2ekernelengine.domain.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import com.example.e2ekernelengine.domain.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.domain.feed.dto.response.FeedPageableResponse;
import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepository;
import com.example.e2ekernelengine.global.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FeedService {

	private final FeedRepository feedRepository;

	private final BlogJpaRepository blogJpaRepository;

	private final FeedSearchRepository feedSearchRepository;

	// TODO: @Autowired 대신 생성자 주입을 사용하자. 인줄 알ㄹ았는데 쓴 이유가 있으신지 물어보기
	//	@Autowired
	//	public FeedService(FeedRepository feedRepository, BlogJpaRepository blogJpaRepository) {
	//		this.feedRepository = feedRepository;
	//		this.blogJpaRepository = blogJpaRepository;
	//	}

	@Transactional
	public Page<FeedPageableResponse> searchFeedsByKeyword(String keyword, Pageable pageable) {

		Page<Feed> page = feedRepository.searchFeedsByKeyword(keyword, pageable);
		// List<Long> searchBlogsByBlogWriterName = blogJpaRepository.findBlogIdsByBlogWriterName(keyword);

// 		return searchFeedsByKeyword.stream()
// 						.map(FeedPageableResponse::fromEntity)
// 						.collect(Collectors.toList());
    
		return page.map(FeedPageableResponse::fromEntity);

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
			Feed feed = feedData.toEntity(blog);
// 			Feed feed = Feed.builder()
// 					.blog(blog)
// 					.feedUrl(feedData.getLink())
// 					.feedTitle(feedData.getTitle())
// 					.feedDescription(feedData.getDescription())
// 					.feedCreatedAt(feedData.getPubDate())
// 					.feedContent(feedData.getContent())
// 					.feedVisitCount(0)
// 					.build();

			feedRepository.save(feed);
			feedSearchRepository.save(feedData.toDocument(blog, feed.getFeedId()));
		}
	}

	public Page<FeedPageableResponse> findRecentFeedList(Pageable pageable) {
		return feedRepository.findAll(pageable).map(FeedPageableResponse::fromEntity);
	}

	public Page<FeedPageableResponse> findMostClickedFeedList(Pageable pageable) {
		return feedRepository.findAll(pageable).map(FeedPageableResponse::fromEntity);
	}

	public void increaseDailyVisitCount(Long feedId) {
		Feed feed = feedRepository.findById(feedId)
						.orElseThrow(() -> new NotFoundException("해당 피드가 존재하지 않습니다"));
		feed.increaseVisitCount();
		feedRepository.save(feed);
	}

}

