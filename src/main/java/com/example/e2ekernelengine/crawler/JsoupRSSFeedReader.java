package com.example.e2ekernelengine.crawler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.example.e2ekernelengine.blog.service.BlogService;
import com.example.e2ekernelengine.feed.service.FeedService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsoupRSSFeedReader {
	// TODO: 빈 등록 or singleton으로 변경
	private final BlogService blogService;
	private final FeedService feedService;

	// /**
	//  * 데이터 콘솔에 출력
	//  * @param blogRssUrl
	//  */
	// public void print(String blogRssUrl) {
	// 	List<FeedData> arr = crawlFeedFromBlog(blogRssUrl, null);
	// 	for (int i = 0; i < arr.size(); i++) {
	// 		System.out.println(arr.get(i).getTitle());
	// 		System.out.println(arr.get(i).getLink());
	// 		System.out.println("pubData: " + arr.get(i).getPubDate());
	// 		System.out.println("description: " + arr.get(i).getDescription());
	// 		System.out.println("content: " + arr.get(i).getContent());
	// 		System.out.println();
	// 	}
	// }

	private Document connectRSSUrlAndGetXML(String rssFeedUrl) {
		Document doc = null;
		try {
			doc = Jsoup.connect(rssFeedUrl).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private BlogData getBlogData(Document doc, String rssFeedUrl) {
		Element element = doc.selectFirst("channel");
		String title = element.selectFirst("title").text();
		String urlLink = element.selectFirst("link").text();
		String description = element.selectFirst("description").text();
		String lastBuildDate = element.selectFirst("lastBuildDate").text();
		BlogData blogData = BlogData.builder()
				.title(title)
				.rssLink(rssFeedUrl)
				.urlLink(urlLink)
				.description(description)
				.lastBuildDate(StringToTimestamp.convertStringToTimestamp(lastBuildDate))
				.lastCrawlDate(new Timestamp(System.currentTimeMillis()))
				.build();

		return blogData;
	}

	private String deleteHtmlTag(String content) {
		String tagPattern = "<[^>]*>";
		Pattern htmlPattern = Pattern.compile(tagPattern);
		Matcher matcher = htmlPattern.matcher(content);
		String textWithoutHtml = matcher.replaceAll("");

		String cssPattern = "\\{[^}]*\\}";
		Pattern cssDetailPattern = Pattern.compile(cssPattern);
		matcher = cssDetailPattern.matcher(textWithoutHtml);
		textWithoutHtml = matcher.replaceAll("");

		return textWithoutHtml;
	}

	private List<FeedData> getNewFeeds(Document document, Timestamp lastCrawlDate) {
		Elements elements = document.select("item");
		List<FeedData> feedDataList = new ArrayList<>();

		for (Element element : elements) {
			Timestamp pubDate = StringToTimestamp.convertStringToTimestamp(element.select("pubDate").text());
			if (lastCrawlDate != null && pubDate.after(lastCrawlDate)) {
				break;
			}
			String link = element.select("link").text();
			String title = element.select("title").text();
			String description = element.select("description").text();
			String content = deleteHtmlTag(element.select("content\\:encoded").text());

			feedDataList.add(FeedData.builder().title(title).link(link).pubDate(pubDate).description(description)
					.content(content).build());
		}
		return feedDataList;
	}

	public List<FeedData> crawlFeedFromBlog(String rssUrl, Timestamp lastCrawlDate) {

		Document document = connectRSSUrlAndGetXML(rssUrl);
		Long blogId = null;
		if (blogService.checkBlogExist(rssUrl)) {
			System.out.println("no");
			blogId = blogService.updateCompanyBlogInfo(getBlogData(document, rssUrl));
		} else {
			System.out.println("here");
			blogId = blogService.saveCompanyBlogInfo(getBlogData(document, rssUrl));
		}

		List<FeedData> feedDataList = getNewFeeds(document, lastCrawlDate);
		feedService.saveNewFeedsByCrawler(feedDataList, blogId);

		return feedDataList;
	}
}
