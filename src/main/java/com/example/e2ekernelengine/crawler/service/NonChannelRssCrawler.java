package com.example.e2ekernelengine.crawler.service;

import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.crawler.dto.FeedDataDto;
import com.example.e2ekernelengine.domain.blog.service.BlogService;
import com.example.e2ekernelengine.domain.feed.service.FeedService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NonChannelRssCrawler extends AbstractRssCrawler {

	private final BlogService blogService;

	private final FeedService feedService;

	private Timestamp convertStringToTimestamp(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
		return getTimestampFromDateStringWithDateFormat(dateString, dateFormat);
	}

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

