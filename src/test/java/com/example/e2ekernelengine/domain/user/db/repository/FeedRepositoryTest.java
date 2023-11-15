package com.example.e2ekernelengine.domain.user.db.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.e2ekernelengine.domain.blog.db.entity.Blog;
import com.example.e2ekernelengine.domain.blog.db.repository.BlogJpaRepository;
import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import com.example.e2ekernelengine.domain.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.global.exception.NotFoundException;

// @ExtendWith(SpringExtension.class)
@DataJpaTest
public class FeedRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private BlogJpaRepository blogJpaRepository;

	@Test
	public void whenFindAllByOrderByVisitCountDesc_thenReturnFeeds() {

		long id = 1;
		Blog blog = blogJpaRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 블로그가 존재하지 않습니다."));
		Feed feed = Feed.builder()
				.blog(blog)
				.feedUrl("https://medium.com/29cm/guide-to-better-dev-culture-590e905e2f5b?source=rss-685a7f2170------2")
				.feedTitle("Guide to Better Dev Culture")
				.feedContent(
						"믿음직한 라이프스타일 가이드 29CM의 개발 조직은 어떻게 협업하고 있을까요?비즈니스의 성장과 동시에 신뢰있는 협업을 만들어가는 29 PEOPLE 이야기.5년 연속 평균 거래액이 70% 이상 증가하...")
				.visitCount(10)
				.build();

		entityManager.persist(feed);
		entityManager.flush();

		Pageable pageable = PageRequest.of(0, 5, Sort.by("visitCount").descending());
		Page<Feed> foundFeeds = feedRepository.findAllByOrderByVisitCountDesc(pageable);
		assertThat(foundFeeds).isNotEmpty();
	}
}