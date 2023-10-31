package com.example.e2ekernelengine.crawler.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RssCrawler {

	private final ChannelRssCrawler channelRssCrawler;
	private final NonChannelRssCrawler nonChannelRssCrawler;

	private Document connectRSSUrlAndGetXML(String rssFeedUrl) {
		Document doc = null;
		try {
			doc = Jsoup.connect(rssFeedUrl).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private Boolean isChannelTagExist(Document doc) {
		return doc.selectFirst("channel") != null;
	}

	public void assignRssCrawler(String rssUrl) {
		Document document = connectRSSUrlAndGetXML(rssUrl);
		if (isChannelTagExist(document)) {
			// TODO: lastCrawledDate 변경하기
			channelRssCrawler.crawlFeedFromBlog(document, rssUrl, null);
		} else {
			System.out.println("here");
			nonChannelRssCrawler.crawlFeedFromBlog(document, rssUrl, null);
		}
	}
}
