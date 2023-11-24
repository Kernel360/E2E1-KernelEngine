package com.example.e2ekernelengine.crawler.service;

import java.sql.Timestamp;
import java.util.List;

import org.jsoup.nodes.Document;

import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.crawler.dto.FeedDataDto;

public interface RssCrawler {

	BlogDataDto getBlogData(Document document, String rssUrl);
	
	List<FeedDataDto> getNewFeedList(Document document, Timestamp lastCrawlDate);
}