package com.example.e2ekernelengine.crawler;

import java.sql.Timestamp;
import java.util.List;

public interface IRssCrawler {

	List<FeedDataDto> crawlFeedFromBlog(String rssUrl, Timestamp lastCrawlDate);
}
