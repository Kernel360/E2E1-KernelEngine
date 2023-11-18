package com.example.e2ekernelengine.crawler.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRssCrawler implements IRssCrawler {

	// protected Document connectRSSUrlAndGetXML(String rssFeedUrl) {
	// 	Document doc = null;
	// 	try {
	// 		doc = Jsoup.connect(rssFeedUrl).get();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// 	return doc;
	// }

	protected static Timestamp getTimestampFromDateStringWithDateFormat(String dateString, SimpleDateFormat dateFormat) {
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date parsedDate = dateFormat.parse(dateString);
			return new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			log.error(String.valueOf(e));
			return null;
		}
	}

	protected String deleteHtmlTag(String content) {
		String tagPattern = "<[^>]*>";
		Pattern htmlPattern = Pattern.compile(tagPattern);
		Matcher matcher = htmlPattern.matcher(content);
		String textWithoutTag = matcher.replaceAll("");

		return textWithoutTag;
	}

	protected String deleteCssPattern(String content) {
		String cssPattern = "\\{[^}]*\\}";
		Pattern cssDetailPattern = Pattern.compile(cssPattern);
		Matcher matcher = cssDetailPattern.matcher(content);
		String textWithoutCss = matcher.replaceAll("");

		return textWithoutCss;
	}

	protected Boolean isDescriptionOverLimit(String content, int limit) {
		return content.length() > limit;
	}
}
