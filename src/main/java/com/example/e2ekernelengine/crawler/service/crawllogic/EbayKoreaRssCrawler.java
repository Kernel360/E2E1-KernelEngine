package com.example.e2ekernelengine.crawler.service.crawllogic;

import java.sql.Timestamp;
import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.crawler.service.RssCrawler;
import com.example.e2ekernelengine.crawler.util.BlogTagDto;
import com.example.e2ekernelengine.crawler.util.FeedTagDto;
import com.example.e2ekernelengine.crawler.util.RssCrawlerUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EbayKoreaRssCrawler implements RssCrawler {

	private final String dateFormat = "EEE, dd MMM yyyy HH:mm:ss z";

	@Override
	public BlogDataDto getBlogData(Document document, String rssUrl) {
		return RssCrawlerUtil.getBlogData(document, rssUrl, dateFormat,
				BlogTagDto.of("channel", "title", "link", "description", "pubDate"));
	}

	@Override
	public List<FeedDataDto> getNewFeedList(Document document, Timestamp lastCrawlDate) {
		return RssCrawlerUtil.getNewFeedList(document, lastCrawlDate, dateFormat,
				FeedTagDto.of("item", "pubDate", "link", "title", "description", "content\\:encoded"));
	}
}
