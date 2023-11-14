package com.example.modulebatch.job;

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

@Configuration
@RequiredArgsConstructor
public class AddFeedFromBlogJobConfig {
	private final JobBuilderFactory jbf;
	private final StepBuilderFactory sbf;
	private final RssCrawler rssCrawler;


	@Bean
	public Job addFeedFromBlogJob() {
		System.out.println("Job 시작");
		return jbf.get("addFeedFromBlogJob")
				.start(addFeedFromBlogStep())
				.build();
	}

	@Bean
	public Step addFeedFromBlogStep() {
		System.out.println("Step 시작");
		return sbf.get("addFeedFromBlogStep")
				.tasklet((contribution, chunkContext) -> {
					DataLoader dataLoader = new DataLoader(rssCrawler);
					dataLoader.loadInitialData();
					return RepeatStatus.FINISHED;
					// List<BlogResponseDto> blogList = blogService.findAllBlog();
					// for (BlogResponseDto dto : blogList) {
					// 	rssCrawler.assignRssCrawler(dto.getRss());
					// 	System.out.println("[Batch] : 블로그 피드 업데이트 중");
					// }
					// System.out.println("[Batch] : 블로그 피드 업데이트 완료");
					// return RepeatStatus.FINISHED;
				})
				.build();
	}

}
