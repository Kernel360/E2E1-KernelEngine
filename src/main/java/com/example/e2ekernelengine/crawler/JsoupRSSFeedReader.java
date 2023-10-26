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

public class JsoupRSSFeedReader {
	private static final JsoupRSSFeedReader instance = new JsoupRSSFeedReader();

	private JsoupRSSFeedReader() {
	}

	public static JsoupRSSFeedReader getInstance() {
		return instance;
	}

	/**
	 *  테스트를 위한 메인메소드
	 */
	public static void main(String[] args) {
		JsoupRSSFeedReader r = new JsoupRSSFeedReader();
		r.print();

	}

	/**
	 * 데이터 콘솔에 출력
	 */
	public void print() {
		// List<FeedData> arr = crawlFeedFromBlog("https://toss.tech/rss.xml", null);
		List<FeedData> arr = crawlFeedFromBlog("https://techblog.woowahan.com/feed/", null);
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i).title);
			System.out.println(arr.get(i).link);
			System.out.println("pubData: " + arr.get(i).pubDate);
			System.out.println("description: " + arr.get(i).description);
			System.out.println("content: " + arr.get(i).content);
			System.out.println();
		}
	}

	private Document connectRSSUrlAndGetXML(String rssFeedUrl) {
		Document doc = null;
		try {
			doc = Jsoup.connect(rssFeedUrl).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private BlogData getBlogData(Document doc) {
		Element element = doc.selectFirst("channel");
		String title = element.selectFirst("title").text();
		String link = element.selectFirst("link").text();
		String description = element.selectFirst("description").text();
		String lastBuildDate = element.selectFirst("lastBuildDate").text();
		BlogData blogData = BlogData.builder()
				.title(title)
				.link(link)
				.description(description)
				.lastBuildDate(lastBuildDate)
				.lastCrawlDate(new Timestamp(System.currentTimeMillis()))
				.build();

		return blogData;
	}

	private String deleteHtmlTag(String content) {
		// 정규식 패턴
		String tagPattern = "<[^>]*>";
		Pattern htmlPattern = Pattern.compile(tagPattern);
		// 정규식을 사용하여 HTML 태그 삭제
		Matcher matcher = htmlPattern.matcher(content);
		String textWithoutHtml = matcher.replaceAll("");

		String cssPattern = "\\{[^}]*\\}";
		Pattern cssDetailPattern = Pattern.compile(cssPattern);
		matcher = cssDetailPattern.matcher(textWithoutHtml);
		textWithoutHtml = matcher.replaceAll("");
		// 결과 출력
		return textWithoutHtml;
	}

	private List<FeedData> getNewFeeds(Document document, Timestamp lastCrawlDate) {
		Elements elements = document.select("item");
		List<FeedData> feedDataList = new ArrayList<>();

		for (Element element : elements) {
			String pubDate = element.select("pubDate").text();
			if (lastCrawlDate != null && StringToTimestamp.convertStringToTimestamp(pubDate).after(lastCrawlDate)) {
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

	/**
	 *  블로그의 최근 포스트 5개를 배열로 반환
	 * @return
	 */
	public List<FeedData> crawlFeedFromBlog(String rssFeedUrl, Timestamp lastCrawlDate) {

		Document document = connectRSSUrlAndGetXML(rssFeedUrl);
		BlogData blogData = getBlogData(document);
		List<FeedData> feedDataList = getNewFeeds(document, lastCrawlDate);

		return feedDataList;
	}
}
