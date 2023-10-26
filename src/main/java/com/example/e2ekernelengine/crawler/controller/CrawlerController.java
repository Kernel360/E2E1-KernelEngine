package com.example.e2ekernelengine.crawler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.crawler.JsoupRSSFeedReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crawler")
public class CrawlerController {
	private final JsoupRSSFeedReader crawler;

	@GetMapping("/test")
	public void test() {
		crawler.print();
	}
}
