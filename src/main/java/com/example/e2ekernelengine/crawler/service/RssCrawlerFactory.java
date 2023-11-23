package com.example.e2ekernelengine.crawler.service;

import org.jsoup.nodes.Document;

import com.example.e2ekernelengine.crawler.service.crawllogic.ChannelPubDateRssCrawler;
import com.example.e2ekernelengine.crawler.service.crawllogic.ChannelRssCrawler;
import com.example.e2ekernelengine.crawler.service.crawllogic.CheeseYunRssCrawler;
import com.example.e2ekernelengine.crawler.service.crawllogic.NaverD2RssCrawler;
import com.example.e2ekernelengine.crawler.service.crawllogic.NonChannelRssCrawler;
import com.example.e2ekernelengine.crawler.util.BeanUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RssCrawlerFactory {

	private RssCrawlerFactory() {
	}

	public static RssCrawler assignCrawler(Document document) {

		String crawlerType = getCrawlerType(document);
		if (crawlerType.equals("channel")) {
			return (ChannelRssCrawler)BeanUtil.getBean(ChannelRssCrawler.class);
		} else if (crawlerType.equals("non-channel")) {
			return (NonChannelRssCrawler)BeanUtil.getBean(NonChannelRssCrawler.class);
		} else if (crawlerType.equals("channel-pubdate")) {
			return (ChannelPubDateRssCrawler)BeanUtil.getBean(ChannelPubDateRssCrawler.class);
		} else if (crawlerType.equals("cheese-yun")) {
			return (CheeseYunRssCrawler)BeanUtil.getBean(CheeseYunRssCrawler.class);
		} else if (crawlerType.equals("naver-d2")) {
			return (NaverD2RssCrawler)BeanUtil.getBean(NaverD2RssCrawler.class);
		} else {
			throw new RuntimeException("No crawler");
		}
	}

	private static String getCrawlerType(Document document) {
		String title = document.selectFirst("title").text();
		if (title.contains("Yun Blog")) {
			return "cheese-yun";
		} else if (title.contains("D2 Blog")) {
			return "naver-d2";
		} else if (document.selectFirst("channel") != null) {
			if (document.selectFirst("lastBuildDate") != null) {
				return "channel";
			} else {
				return "channel-pubdate";
			}
		} else if (document.selectFirst("feed") != null) {
			return "non-channel";
		} else {
			throw new RuntimeException("No crawler");
		}
	}
}
