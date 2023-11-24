package com.example.e2ekernelengine.crawler.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RssCrawler {

	private final ChannelRssCrawler channelRssCrawler;
	private final NonChannelRssCrawler nonChannelRssCrawler;

	/**
	 * @param rssUrl rssURL of the tech blog
	 */
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

	/**
	 * @param rssFeedUrl rss에서 발췌한 피드의 URL
	 * @return Jsoup Document 객체
	 */
	private Document connectRSSUrlAndGetXML(String rssFeedUrl) {
		Document doc = null;
		try {
			doc = Jsoup.connect(rssFeedUrl).get();
		} catch (IOException e) {
			log.error(String.valueOf(e));
		}
		return doc;
	}

	private Boolean isChannelTagExist(Document doc) {
		return doc.selectFirst("channel") != null;
	}

}
