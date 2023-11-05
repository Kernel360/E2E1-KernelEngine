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
public class NonChannelRssCrawler extends AbstractRssCrawler implements IRssCrawler {
	private final BlogService blogService;
	private final FeedService feedService;

	// public static void main(String[] args) {
	// 	NonChannelRssCrawler r = new NonChannelRssCrawler();
	// 	r.print();
	// }
	//
	// /**
	//  * 데이터 콘솔에 출력
	//  */
	// public void print() {
	// 	// List<FeedDataDto> arr = crawlFeedFromBlog("https://hyperconnect.github.io/feed.xml", null);
	// 	List<FeedDataDto> arr = test();
	// 	for (int i = 0; i < arr.size(); i++) {
	// 		System.out.println(arr.get(i).getTitle());
	// 		System.out.println(arr.get(i).getLink());
	// 		System.out.println("pubData: " + arr.get(i).getPubDate());
	// 		System.out.println("description: " + arr.get(i).getDescription());
	// 		System.out.println("content: " + arr.get(i).getContent());
	// 		System.out.println();
	// 	}
	// }
	//
	// private Document connectRSSUrlAndGetXML(String rssFeedUrl) {
	// 	Document doc = null;
	// 	try {
	// 		doc = Jsoup.connect(rssFeedUrl).get();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// 	return doc;
	// }

	private Timestamp convertStringToTimestamp(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		try {
			Date parsedDate = dateFormat.parse(dateString);
			return new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public List<FeedDataDto> test() {
	// 	// Document doc = connectRSSUrlAndGetXML("https://engineering-skcc.github.io/feed.xml");
	// 	// BlogDataDto blogDataDto = getBlogData(doc, "https://engineering-skcc.github.io/feed.xml");
	// 	Document doc = connectRSSUrlAndGetXML("https://hyperconnect.github.io/feed.xml");
	// 	BlogDataDto blogDataDto = getBlogData(doc, "https://hyperconnect.github.io/feed.xml");
	// 	System.out.println(blogDataDto.toString());
	//
	// 	return getNewFeeds(doc, null);
	//
	// }

	private BlogDataDto getBlogData(Document doc, String rssFeedUrl) {
		Element element = doc.selectFirst("feed");
		String title = element.selectFirst("title").text();
		String urlLink = element.selectFirst("link").text();
		String description = element.selectFirst("subtitle").text();
		String lastBuildDate = element.selectFirst("updated").text();
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
		Elements elements = document.select("entry");
		List<FeedDataDto> feedDataList = new ArrayList<>();

		for (Element element : elements) {
			Timestamp pubDate = convertStringToTimestamp(element.select("published").text());
			if (lastCrawlDate != null && pubDate.after(lastCrawlDate)) {
				break;
			}
			String link = element.select("link").text();
			String title = element.select("title").text();
			String description = deleteCssPattern(deleteHtmlTag(element.select("summary").text()));
			String content = "";
			if (isDescriptionOverLimit(description, 200)) {
				content = description;
				description = content.substring(0, 200);
			} else {
				String str = element.select("content").text();
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
	public List<FeedDataDto> crawlFeedFromBlog(Document document, String rssUrl, Timestamp lastCrawlDate) {
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

