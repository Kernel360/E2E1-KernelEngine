package com.example.e2ekernelengine.crawler.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.crawler.util.BeanUtil;
import com.example.e2ekernelengine.crawler.util.RssCrawlerUtil;
import com.example.e2ekernelengine.domain.blog.db.entity.Blog;
import com.example.e2ekernelengine.domain.blog.service.BlogCrawlerService;
import com.example.e2ekernelengine.domain.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.domain.feed.service.FeedCrawlerService;
import com.example.e2ekernelengine.domain.user.db.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public class CustomRssCrawler {

	private final Document document;
	private final String rssUrl;
	private final RssCrawler crawler;
	private final BlogOwnerType blogOwnerType;
	private final BlogCrawlerService blogCrawlerService;
	private final FeedCrawlerService feedCrawlerService;

	private final User user;

	@Builder
	public CustomRssCrawler(String rssUrl, BlogOwnerType blogOwnerType, User user) {
		this.document = RssCrawlerUtil.connectRSSUrlAndGetXML(rssUrl);
		this.crawler = RssCrawlerFactory.assignCrawler(document);
		this.rssUrl = rssUrl;
		this.blogOwnerType = blogOwnerType;
		this.blogCrawlerService = (BlogCrawlerService)BeanUtil.getBean(BlogCrawlerService.class);
		this.feedCrawlerService = (FeedCrawlerService)BeanUtil.getBean(FeedCrawlerService.class);
		this.user = user;
	}

	public static CustomRssCrawler of(String rssUrl, BlogOwnerType blogOwnerType) {
		return CustomRssCrawler.builder()
				.rssUrl(rssUrl)
				.blogOwnerType(blogOwnerType)
				.build();
	}

	public Blog saveBlogInfo() {
		BlogDataDto blogData = crawler.getBlogData(document, rssUrl);
		if (blogCrawlerService.checkBlogExist(rssUrl)) {
			return blogCrawlerService.updateBlogInfo(blogData, blogOwnerType);
		} else {
			return blogCrawlerService.saveBlogInfo(user, blogData, blogOwnerType);
		}
	}

	public void saveFeedInfo(Blog blog) {
		List<FeedDataDto> feedDataList = crawler.getNewFeedList(document, blog.getBlogLastCrawlAt());
		feedCrawlerService.saveNewFeedList(feedDataList, blog.getBlogId());
		blog.updateLastCrawlAt();
	}

	// TODO: 블로그 저장과 피드 크롤링해오는 통합 메서드
	public void crawl() {
		Blog blog = saveBlogInfo();
		saveFeedInfo(blog);
	}
}
