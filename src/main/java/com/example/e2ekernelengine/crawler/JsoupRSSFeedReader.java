package com.example.e2ekernelengine.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupRSSFeedReader {
	private final int rssCnt = 5; //가져올 포스트 개수
	private final String rssFeed = "https://toss.tech/rss.xml"; //rss 주소

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

		System.out.println("in");
		PostData[] arr = getPosts();
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i].title);
			System.out.println(arr[i].link);
			System.out.println("pubData: " + arr[i].pubDate);
			System.out.println("description: " + arr[i].description);
			System.out.println("content: " + arr[i].content);
			System.out.println();
		}
	}

	/**
	 *  블로그의 최근 포스트 5개를 배열로 반환
	 */
	public PostData[] getPosts() {
		System.out.println("ibn");
		PostData[] postArray = new PostData[rssCnt];

		try {
			Document doc = Jsoup.connect(rssFeed).get();
			// TODO: 블로그 정보에 대한 글 가져오기
			// Elements element = doc.select("");
			Elements elements = doc.select("item");

			for (int i = 0; i < rssCnt; i++) {
				Element element = null;
				try {
					element = elements.get(i);
				} catch (IndexOutOfBoundsException e) {
					break;
				}
				String link = element.select("link").text();
				String title = element.select("title").text();
				String pubDate = element.select("pubDate").text();
				String description = element.select("description").text();
				String content = element.select("content\\:encoded").text();

				// Element e_channel = root.getChild("channel");
				// List<Element> children = e_channel.getChildren("item");
				// Iterator<Element> iter = children.iterator();
				//
				// String link = e.getChildTextTrim("link");
				// String title = e.getChildTextTrim("title");
				// String pubDate = e.getChildTextTrim("pubDate");
				// String description = e.getChildTextTrim("description");
				// Element contentEncodedElement = e.getChild("encoded", Namespace.getNamespace("content"));
				// String content = null;
				// if (contentEncodedElement != null) {
				// 	System.out.println("contentEncodedElement: " + contentEncodedElement);
				//
				// 	CDATA cdata = (CDATA)contentEncodedElement.getContent(0);
				// 	content = cdata.getText();
				// }
				//
				postArray[i] = new PostData(link, title, pubDate, description, content);
			}

			// } catch (JDOMException e) {
			// 	e.printStackTrace();
			// } catch (IOException e) {
			// 	e.printStackTrace();
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return postArray;
	}
}
