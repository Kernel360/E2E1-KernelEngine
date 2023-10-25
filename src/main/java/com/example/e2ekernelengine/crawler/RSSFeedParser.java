package com.example.e2ekernelengine.crawler;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class RSSFeedParser {

	private final int rssCnt = 5; //가져올 포스트 개수
	private final String rssFeed = "https://toss.tech/rss.xml"; //rss 주소

	/**
	 *  테스트를 위한 메인메소드
	 */
	// public static void main(String[] args) {
	// 	RSSFeedParser r = new RSSFeedParser();
	// 	r.print();
	//
	// }

	/**
	 * 데이터 콘솔에 출력
	 */
	public void print() {

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
		PostData[] postArray = new PostData[rssCnt];

		SAXBuilder saxBuilder = new SAXBuilder();

		try {
			Document doc = saxBuilder.build(rssFeed);
			Element root = doc.getRootElement();

			Element e_channel = root.getChild("channel");
			List<Element> children = e_channel.getChildren("item");
			Iterator<Element> iter = children.iterator();
			for (int i = 0; i < rssCnt; i++) {
				if (!iter.hasNext())
					break;
				Element e = iter.next();

				String link = e.getChildTextTrim("link");
				String title = e.getChildTextTrim("title");
				String pubDate = e.getChildTextTrim("pubDate");
				String description = e.getChildTextTrim("description");
				Element contentEncodedElement = e.getChild("encoded", Namespace.getNamespace("content"));
				String content = null;
				if (contentEncodedElement != null) {
					System.out.println("contentEncodedElement: " + contentEncodedElement);

					CDATA cdata = (CDATA)contentEncodedElement.getContent(0);
					content = cdata.getText();
				}

				postArray[i] = new PostData(link, title, pubDate, description, content);
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return postArray;
	}
}
