package com.example.e2ekernelengine.crawler;

/**
 * 하나의 포스트 정보를 담을수있는 객체
 */
public class PostData {

		String link;
		String title;
		String description;
		String pubDate;
		String content;


		public PostData(String link, String title, String pubDate, String description, String content) {
			super();
			this.link = link;
			this.title = title;
			this.pubDate = pubDate;
			this.description = description;
			this.content = content;
		}
}
