package com.example.e2ekernelengine.crawler.service;

import java.sql.Timestamp;
import java.util.List;

import org.jsoup.nodes.Document;

import com.example.e2ekernelengine.crawler.dto.FeedDataDto;

public interface IRssCrawler {

	List<FeedDataDto> crawlFeedFromBlog(Document document, String rssUrl, Timestamp lastCrawlDate);
}
