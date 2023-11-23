package com.example.e2ekernelengine.crawler.service;

import org.jsoup.nodes.Document;

import com.example.e2ekernelengine.crawler.service.crawllogic.ChannelRssCrawler;
import com.example.e2ekernelengine.crawler.service.crawllogic.EbayKoreaRssCrawler;
import com.example.e2ekernelengine.crawler.service.crawllogic.NonChannelRssCrawler;
import com.example.e2ekernelengine.crawler.service.crawllogic.PosTypeRssCrawler;
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
		} else if (crawlerType.equals("nonChannel")) {
			return (NonChannelRssCrawler)BeanUtil.getBean(NonChannelRssCrawler.class);
		} else if (crawlerType.equals("ebay-korea")) {
			return (EbayKoreaRssCrawler)BeanUtil.getBean(EbayKoreaRssCrawler.class);
		} else if (crawlerType.equals("postype")) {
			return (PosTypeRssCrawler)BeanUtil.getBean(PosTypeRssCrawler.class);
		} else {
			throw new RuntimeException("No crawler");
		}
	}

	private static String getCrawlerType(Document document) {
		String title = document.selectFirst("title").text();
		if (title.equals("지마켓 기술블로그")) {
			return "ebay-korea";
		} else if (title.contains("포스타입 팀")) {
			return "postype";
		} else if (document.selectFirst("channel") != null) {
			return "channel";
		} else if (document.selectFirst("feed") != null) {
			return "nonChannel";
		} else {
			throw new RuntimeException("No crawler");
		}
	}
}
