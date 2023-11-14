package com.example.modulebatch.feed.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import com.example.modulebatch.feed.db.entity.FeedStatistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MakeFeedStatisticsJobConfig {
	private final JobBuilderFactory jbf;
	private final StepBuilderFactory sbf;
	private final EntityManagerFactory emf;

	@Bean
	public Job makeStatisticsJob() {
		Flow makeDailyStatisticsFlow = new FlowBuilder<Flow>("makeDailyStatisticsFlow")
				.start(makeDailyVisitCountStep())
				.build();
		Flow resetFeedVisitCountFlow = new FlowBuilder<Flow>("resetFeedVisitCountFlow")
				.start(resetFeedVisitCountStep())
				.build();

		return this.jbf.get("makeStatisticsJob")
				.start(makeDailyStatisticsFlow)
				.next(resetFeedVisitCountFlow)
				.build()
				.build();
	}

	@Bean
	public Step makeDailyVisitCountStep() {
		return sbf.get("makeDailyVisitCountStep")
				.<Feed, FeedStatistics>chunk(20)
				.reader(makeDailyVisitCountItemReader())
				.processor(makeDailyVisitCountItemProcessor())
				.writer(makeDailyVisitCountItemWriter())
				.build();
	}

	@Bean
	@StepScope
	public JpaCursorItemReader<Feed> makeDailyVisitCountItemReader() {
		return new JpaCursorItemReaderBuilder<Feed>()
				.name("makeDailyVisitCountItemReader")
				.entityManagerFactory(emf)
				.queryString("select f from Feed f")
				.build();
	}

	@Bean
	@StepScope
	public ItemProcessor<Feed, FeedStatistics> makeDailyVisitCountItemProcessor() {
		return FeedStatistics::create;
	}

	@Bean
	@StepScope
	public JpaItemWriter<FeedStatistics> makeDailyVisitCountItemWriter() {
		return new JpaItemWriterBuilder<FeedStatistics>()
				.entityManagerFactory(emf)
				.build();
	}

	@Bean
	public Step resetFeedVisitCountStep() {
		return sbf.get("resetFeedVisitCountStep")
				.<Feed, Feed>chunk(20)
				.reader(resetFeedVisitCountItemReader())
				.processor(resetFeedVisitCountItemProcessor())
				.writer(resetFeedVisitCountItemWriter())
				.build();
	}

	@Bean
	@StepScope
	public JpaCursorItemReader<Feed> resetFeedVisitCountItemReader() {
		return new JpaCursorItemReaderBuilder<Feed>()
				.name("resetFeedVisitCountItemReader")
				.entityManagerFactory(emf)
				.queryString("select f from Feed f where f.accessCount > 0")
				.build();
	}

	@Bean
	@StepScope
	public ItemProcessor<Feed, Feed> resetFeedVisitCountItemProcessor() {
		return item -> {
			item.resetVisitCount();
			return item;
		};
	}

	@Bean
	@StepScope
	public JpaItemWriter<Feed> resetFeedVisitCountItemWriter() {
		return new JpaItemWriterBuilder<Feed>()
				.entityManagerFactory(emf)
				.build();
	}
}
