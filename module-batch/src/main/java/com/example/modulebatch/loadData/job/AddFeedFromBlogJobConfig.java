package com.example.modulebatch.loadData.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.e2ekernelengine.DataLoader;
import com.example.e2ekernelengine.crawler.service.RssCrawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AddFeedFromBlogJobConfig {
	private final JobBuilderFactory jbf;
	private final StepBuilderFactory sbf;
	private final RssCrawler rssCrawler;
	// private final RssParser rssParser;
	private final EntityManagerFactory emf;

	@Bean
	public Job addFeedFromDataLoaderJob() {
		log.info("Job 시작");
		return jbf.get("addFeedFromBlogJob")
				.start(addFeedFromDataLoaderStep())
				.build();
	}

	@Bean
	public Step addFeedFromDataLoaderStep() {
		log.info("Step 시작");
		return sbf.get("addFeedFromBlogStep")
				.tasklet((contribution, chunkContext) -> {
					DataLoader dataLoader = new DataLoader(rssCrawler);
					dataLoader.loadInitialData();
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	// 추후 마무리 예정
	// @Bean
	// public Job addFeedFromBlogJob() {
	// 	log.info("블로그 리스트로 읽기 Job 시작");
	// 	return jbf.get("addFeedFromBlogJob")
	// 			.start(addFeedFromBlogStep())
	// 			.build();
	// }
	//
	// @Bean
	// public Step addFeedFromBlogStep() {
	// 	return sbf.get("addFeedFromBlogStep")
	// 			.chunk(10)
	// 			.reader(addFeedFromBlogItemReader())
	// 			.processor(addFeedFromBlogItemProcessor())
	// 			.writer(addFeedFromBlogItemWriter())
	// 			.build();
	// }

	// @Bean
	// @StepScope
	// public JpaPagingItemReader<Blog> addFeedFromBlogItemReader() {
	// 	return new JpaPagingItemReaderBuilder<Blog>()
	// 			.name("addFeedFromBlogItemReader")
	// 			.entityManagerFactory(emf)
	// 			.queryString("select b from Blog b")
	// 			.pageSize(10)
	// 			.build();
	// }
	//
	// @Bean
	// @StepScope
	// public ItemProcessor<Blog, List<FeedDataDto>> addFeedFromBlogItemProcessor() {
	// 	return item -> rssParser.assignRssParser(item.getBlogRssUrl());
	// }

	// @Bean
	// public JpaItemWriter<List<FeedDataDto>> addFeedFromBlogItemWriter() {
	//
	// }

}

