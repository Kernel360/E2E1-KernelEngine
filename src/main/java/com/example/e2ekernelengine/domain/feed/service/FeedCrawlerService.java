package com.example.e2ekernelengine.domain.feed.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.domain.blog.db.entity.Blog;
import com.example.e2ekernelengine.domain.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import com.example.e2ekernelengine.domain.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.domain.search.repository.EsFeedSearchRepository;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedCrawlerService {

	private final FeedRepository feedRepository;
	private final BlogJpaRepository blogJpaRepository;
	private final EsFeedSearchRepository esFeedSearchRepository;

	@Transactional
	public void saveNewFeedList(List<FeedDataDto> feedDataList, Long blogId) {

		Blog blog = blogJpaRepository.findById(blogId).orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));
		for (FeedDataDto feedData : feedDataList) {
			Feed feed = feedData.toEntity(blog);
			feedRepository.save(feed);
			esFeedSearchRepository.save(feedData.toDocument(blog, feed.getFeedId()));
		}
	}
}
