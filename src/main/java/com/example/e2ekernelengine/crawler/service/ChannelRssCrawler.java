package com.example.e2ekernelengine.crawler.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.example.e2ekernelengine.blog.service.BlogService;
import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.feed.service.FeedService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChannelRssCrawler extends AbstractRssCrawler implements IRssCrawler {
	private final BlogService blogService;
	private final FeedService feedService;

	private Timestamp convertStringToTimestamp(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date parsedDate = dateFormat.parse(dateString);
			return new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private BlogDataDto getBlogData(Document doc, String rssFeedUrl) {
		Element element = doc.selectFirst("channel");
		String title = element.selectFirst("title").text();
		String urlLink = element.selectFirst("link").text();
		String description = element.selectFirst("description").text();
		String lastBuildDate = element.selectFirst("lastBuildDate").text();
		return BlogDataDto.builder()
				.title(title)
				.rssLink(rssFeedUrl)
				.urlLink(urlLink)
				.description(description)
				.lastBuildDate(convertStringToTimestamp(lastBuildDate))
				.lastCrawlDate(new Timestamp(System.currentTimeMillis()))
				.build();
	}

	private List<FeedDataDto> getNewFeeds(Document document, Timestamp lastCrawlDate) {
		Elements elements = document.select("item");
		List<FeedDataDto> feedDataList = new ArrayList<>();

		for (Element element : elements) {
			Timestamp pubDate = convertStringToTimestamp(element.select("pubDate").text());
			if (lastCrawlDate != null && pubDate.after(lastCrawlDate)) {
				break;
			}
			String link = element.select("link").text();
			String title = element.select("title").text();
			String description = element.select("description").text();
			String content = "";
			if (isDescriptionOverLimit(description, 50)) {
				content = deleteCssPattern(deleteHtmlTag(description));
				description = "";
			} else {
				String str = element.select("content\\:encoded").text();
				if (str != null) {
					content = deleteCssPattern(deleteHtmlTag(str));
				}
			}

			feedDataList.add(FeedDataDto.builder().title(title).link(link).pubDate(pubDate).description(description)
					.content(content).build());
		}
		return feedDataList;
	}

	@Override
	public List<FeedDataDto> crawlFeedFromBlog(String rssUrl, Timestamp lastCrawlDate) {

		Document document = connectRSSUrlAndGetXML(rssUrl);
		Long blogId = null;
		if (blogService.checkBlogExist(rssUrl)) {
			blogId = blogService.updateCompanyBlogInfo(getBlogData(document, rssUrl));
		} else {
			blogId = blogService.saveCompanyBlogInfo(getBlogData(document, rssUrl));
		}

		List<FeedDataDto> feedDataList = getNewFeeds(document, lastCrawlDate);
		feedService.saveNewFeedsByCrawler(feedDataList, blogId);

		return feedDataList;
	}
}
