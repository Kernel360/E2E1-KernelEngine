package com.example.e2ekernelengine.crawler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.example.e2ekernelengine.blog.service.BlogService;
import com.example.e2ekernelengine.feed.service.FeedService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChannelRssCrawler extends AbstractRssCrawler implements IRssCrawler {
	// TODO: 빈 등록 or singleton으로 변경
	private final BlogService blogService;
	private final FeedService feedService;

	// /**
	//  * 데이터 콘솔에 출력
	//  * @param blogRssUrl
	//  */
	// public void print(String blogRssUrl) {
	// 	List<FeedDataDto> arr = crawlFeedFromBlog(blogRssUrl, null);
	// 	for (int i = 0; i < arr.size(); i++) {
	// 		System.out.println(arr.get(i).getTitle());
	// 		System.out.println(arr.get(i).getLink());
	// 		System.out.println("pubData: " + arr.get(i).getPubDate());
	// 		System.out.println("description: " + arr.get(i).getDescription());
	// 		System.out.println("content: " + arr.get(i).getContent());
	// 		System.out.println();
	// 	}
	// }

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
				.lastBuildDate(StringToTimestamp.convertStringToTimestamp(lastBuildDate))
				.lastCrawlDate(new Timestamp(System.currentTimeMillis()))
				.build();
	}

	private List<FeedDataDto> getNewFeeds(Document document, Timestamp lastCrawlDate) {
		Elements elements = document.select("item");
		List<FeedDataDto> feedDataList = new ArrayList<>();

		for (Element element : elements) {
			Timestamp pubDate = StringToTimestamp.convertStringToTimestamp(element.select("pubDate").text());
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
				content = deleteCssPattern(deleteHtmlTag(element.select("content\\:encoded").text()));
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
