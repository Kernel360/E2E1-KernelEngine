package com.example.e2ekernelengine.crawler.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.crawler.dto.testRssBlogCrawlerDto;
import com.example.e2ekernelengine.crawler.service.crawllogic.ChannelRssCrawler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crawler")
public class CrawlerController {
	private final ChannelRssCrawler crawler;

	@PostMapping("/test")
	public void test(@RequestBody testRssBlogCrawlerDto request) {
		System.out.println(request.getBlogRssUrl());
		// crawler.crawlFeedFromBlog(request.getBlogRssUrl(), null);
	}
}
