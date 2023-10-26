package com.example.e2ekernelengine.feed.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.db.repository.BlogJPARepository;
import com.example.e2ekernelengine.crawler.FeedData;
import com.example.e2ekernelengine.feed.db.entity.Feed;
import com.example.e2ekernelengine.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedService {
	private final FeedRepository feedRepository;
	private final BlogJPARepository blogJPARepository;

	@Transactional
	public void saveNewFeedsByCrawler(List<FeedData> feedDataList, Long blogId) {

		Blog blog = blogJPARepository.findById(blogId).orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));
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

