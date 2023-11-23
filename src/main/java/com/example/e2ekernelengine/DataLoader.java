package com.example.e2ekernelengine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.e2ekernelengine.crawler.service.CustomRssCrawler;
import com.example.e2ekernelengine.domain.blog.util.BlogOwnerType;

@Component
public class DataLoader {

	private final List<String> blogRssUrlList = new ArrayList<>();

	@Autowired
	public DataLoader() {
		blogRssUrlList.add("https://medium.com/feed/myrealtrip-product");
		blogRssUrlList.add("https://medium.com/feed/loplat/tagged/tech");
		blogRssUrlList.add("https://www.bsidesoft.com/feed");
		blogRssUrlList.add("https://bigwaveai.tistory.com/rss");
		blogRssUrlList.add("https://medium.com/feed/idus-tech");
		blogRssUrlList.add("https://medium.com/feed/class101");
		blogRssUrlList.add("https://tech.madup.com/feed");
		blogRssUrlList.add("https://teamdable.github.io/techblog/feed");
		blogRssUrlList.add("https://medium.com/feed/humanscape-tech");
		blogRssUrlList.add("https://zuminternet.github.io/feed");
		blogRssUrlList.add("https://tech.gluesys.com/feed");
		blogRssUrlList.add("https://labs.brandi.co.kr/feed");
		blogRssUrlList.add("https://cntechsystems.tistory.com/rss");
		blogRssUrlList.add("https://musma.github.io/feed");
		blogRssUrlList.add("https://techtopic.skplanet.com/rss");
		blogRssUrlList.add("https://medium.com/feed/cj-onstyle");
		// blogRssUrlList.add("https://spoqa.github.io/rss"); -> description에 데이터 너무 많이 들어옴
		blogRssUrlList.add("https://boostbrothers.github.io/rss");
		blogRssUrlList.add("https://11st-tech.github.io/rss/");
		blogRssUrlList.add("https://makinarocks.github.io/feed");
		blogRssUrlList.add("https://medium.com/feed/prnd");
		blogRssUrlList.add("https://danawalab.github.io/feed");
		blogRssUrlList.add("https://techblog.lotteon.com/feed");
		blogRssUrlList.add("https://medium.com/feed/cloudvillains");
		blogRssUrlList.add("https://saramin.github.io/feed.xml");
		blogRssUrlList.add("https://www.dentium.tech/rss.xml");
		blogRssUrlList.add("https://tech.scatterlab.co.kr/feed.xml");
		blogRssUrlList.add("https://blog.bespinglobal.com/post/category/aws/feed/");
		blogRssUrlList.add("https://hyperconnect.github.io/feed.xml");
		blogRssUrlList.add("https://engineering-skcc.github.io/feed.xml");
		blogRssUrlList.add("https://ebay-korea.tistory.com/rss");
		blogRssUrlList.add("https://team.postype.com/rss");

		// blogRssUrlList.add("https://yanolja.github.io/feed");

		// blogRssUrlList.add("https://meetup.toast.com/rss");
		// blogRssUrlList.add("https://blog.est.ai/feed.xml");

		blogRssUrlList.add("https://helloworld.kurly.com/feed.xml");
		blogRssUrlList.add("https://medium.com/feed/@dev29cm");
		blogRssUrlList.add("https://ridicorp.com/feed/");

		blogRssUrlList.add("https://blog.dramancompany.com/feed/");
		blogRssUrlList.add("https://techblog.yogiyo.co.kr/feed");
		blogRssUrlList.add("https://blog.hwahae.co.kr/rss.xml");
		blogRssUrlList.add("https://medium.com/feed/wantedjobs");
		blogRssUrlList.add("https://techblog.gccompany.co.kr/feed");
		blogRssUrlList.add("https://startlink.blog/feed/");
		blogRssUrlList.add("https://tech.devsisters.com/rss.xml");
		blogRssUrlList.add("https://medium.com/feed/naver-place-dev");
		blogRssUrlList.add("https://medium.com/feed/watcha");
		blogRssUrlList.add("https://netflixtechblog.com/feed");
		blogRssUrlList.add("https://toss.tech/rss.xml");
		blogRssUrlList.add("https://medium.com/feed/daangn");
		blogRssUrlList.add("https://tech.kakao.com/feed/");
		blogRssUrlList.add("https://techblog.lycorp.co.jp/ko/feed/index.xml");
		blogRssUrlList.add("https://blog.banksalad.com/rss.xml");
		blogRssUrlList.add("https://techblog.woowahan.com/feed/");
		blogRssUrlList.add("https://tech.inflab.com/rss.xml");

		// 개인 블로그
		blogRssUrlList.add("https://cheese10yun.github.io/feed.xml");
		blogRssUrlList.add("https://javacan.tistory.com/rss");
		blogRssUrlList.add("https://jojoldu.tistory.com/rss");
		blogRssUrlList.add("https://hyune-c.tistory.com/rss");
		blogRssUrlList.add("https://brunch.co.kr/rss/@@2MrI");

		// 회사
		blogRssUrlList.add("https://d2.naver.com/d2.atom");
		blogRssUrlList.add("https://engineering.linecorp.com/ko/feed/");
		blogRssUrlList.add("https://blog.bytebytego.com/feed");
		blogRssUrlList.add("https://medium.com/feed/coupang-engineering");

		blogRssUrlList.add("https://medium.com/feed/tving-team");
		blogRssUrlList.add("https://tech.kakaoenterprise.com/feed");
		blogRssUrlList.add("https://medium.com/feed/@marcincuber");
		blogRssUrlList.add("http://medium.com/feed/elecle-bike");
		blogRssUrlList.add("https://teamdable.github.io/techblog/feed.xml");
		blogRssUrlList.add("https://netmarble.engineering/feed/");
		blogRssUrlList.add("https://linuxer.name/feed/");
		blogRssUrlList.add("http://feeds2.feedburner.com/rss_outsider_dev");

		blogRssUrlList.add("https://blog.bespinglobal.com/post/category/aws/feed/");
		blogRssUrlList.add("https://aws.amazon.com/ko/blogs/tech/feed/");
		blogRssUrlList.add("https://tech.scatterlab.co.kr/feed");
		blogRssUrlList.add("https://makinarocks.github.io/feed");

	}

	public void loadInitialData() throws InterruptedException {
		CustomRssCrawler customRssCrawler = null;
		try {
			for (String rssUrl : blogRssUrlList) {
				customRssCrawler = new CustomRssCrawler(rssUrl, BlogOwnerType.COMPANY, null);
				customRssCrawler.crawl();
				System.out.println(customRssCrawler.getRssUrl());
			}
		} catch (Exception e) {
			System.out.println(customRssCrawler.getRssUrl());
			e.printStackTrace();
		}
	}
}
