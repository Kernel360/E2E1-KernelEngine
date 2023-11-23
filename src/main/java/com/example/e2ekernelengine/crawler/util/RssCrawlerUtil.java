package com.example.e2ekernelengine.crawler.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.e2ekernelengine.crawler.dto.BlogDataDto;
import com.example.e2ekernelengine.crawler.dto.FeedDataDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RssCrawlerUtil {

	private RssCrawlerUtil() {
	}

	/**
	 * @param dateString: 바꾸고 싶은 날짜 형식의 문자열
	 * @return 변환된 timestamp
	 */
	public static Timestamp convertStringToTimestamp(String dateString, String dateFormatString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString, Locale.ENGLISH);
		return getTimestampFromDateStringWithDateFormat(dateString, dateFormat);
	}

	private static Timestamp getTimestampFromDateStringWithDateFormat(String dateString, SimpleDateFormat dateFormat) {
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date parsedDate = dateFormat.parse(dateString);
			return new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			log.error(String.valueOf(e));
			return null;
		}
	}

	private static String deleteHtmlTag(String content) {
		String tagPattern = "<[^>]*>";
		Pattern htmlPattern = Pattern.compile(tagPattern);
		Matcher matcher = htmlPattern.matcher(content);
		String textWithoutTag = matcher.replaceAll("");

		return textWithoutTag;
	}

	private static String deleteCssPattern(String content) {
		String cssPattern = "\\{[^}]*\\}";
		Pattern cssDetailPattern = Pattern.compile(cssPattern);
		Matcher matcher = cssDetailPattern.matcher(content);
		String textWithoutCss = matcher.replaceAll("");

		return textWithoutCss;
	}

	public static BlogDataDto getBlogData(Document doc, String rssUrl, String dateFormat, BlogTagDto blogTagDto) {
		Element element = doc.selectFirst(blogTagDto.getTypeTag());
		String title = element.selectFirst(blogTagDto.getTitleTag()).text();
		String urlLink = element.selectFirst(blogTagDto.getLinkTag()).text();
		String description = element.selectFirst(blogTagDto.getDescriptionTag()).text();
		String lastBuildDate = element.selectFirst(blogTagDto.getLastBuildDateTag()).text();
		return BlogDataDto.of(title, urlLink, rssUrl, description, convertStringToTimestamp(lastBuildDate, dateFormat));
	}

	public static List<FeedDataDto> getNewFeedList(Document document, Timestamp lastCrawlDate, String dateFormat,
			FeedTagDto feedTagDto) {
		Elements elements = document.select(feedTagDto.getElementTag());
		List<FeedDataDto> feedDataList = new ArrayList<>();

		for (Element element : elements) {
			Timestamp pubDate = convertStringToTimestamp(element.select(feedTagDto.getPubDateTag()).text(), dateFormat);
			if (lastCrawlDate != null && pubDate.after(lastCrawlDate)) {
				break;
			}
			String link = element.select(feedTagDto.getLinkTag()).text();
			String title = element.select(feedTagDto.getTitleTag()).text();
			String description = deleteCssPattern(deleteHtmlTag(element.select(feedTagDto.getDescriptionTag()).text()));
			String content = "";
			if (isDescriptionOverLimit(description, 200)) {
				content = description;
				description = content.substring(0, 200);
			} else {
				String str = element.select(feedTagDto.getContentTag()).text();
				if (str != null) {
					content = deleteCssPattern(deleteHtmlTag(str));
				}
			}

			feedDataList.add(FeedDataDto.of(link, title, pubDate, description, content));
		}
		return feedDataList;
	}

	private static Boolean isDescriptionOverLimit(String content, int limit) {
		return content.length() > limit;
	}

	/**
	 * @param rssFeedUrl rss에서 발췌한 피드의 URL
	 * @return Jsoup Document 객체
	 */
	public static Document connectRSSUrlAndGetXML(String rssFeedUrl) {
		Document document = null;
		try {
			document = Jsoup.connect(rssFeedUrl).get();
		} catch (IOException e) {
			log.error(String.valueOf(e));
		}
		return document;
	}
}
