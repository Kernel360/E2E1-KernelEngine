package com.example.e2ekernelengine.crawler.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractRssCrawler {

	// protected Document connectRSSUrlAndGetXML(String rssFeedUrl) {
	// 	Document doc = null;
	// 	try {
	// 		doc = Jsoup.connect(rssFeedUrl).get();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// 	return doc;
	// }

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
