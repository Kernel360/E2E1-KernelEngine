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
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
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

		return this.jbf.get("makeStatisticsJob")
				.start(makeDailyStatisticsFlow)
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
	public JpaPagingItemReader<Feed> makeDailyVisitCountItemReader() {
		return new JpaPagingItemReaderBuilder<Feed>()
				.name("makeDailyVisitCountItemReader")
				.entityManagerFactory(emf)
				.queryString("select f from Feed f")
				.build();
	}

	@Bean
	@StepScope
	public ItemProcessor<Feed, FeedStatistics> makeDailyVisitCountItemProcessor() {
		return item -> {
			FeedStatistics statistics = FeedStatistics.create(item);
			if (statistics.getVisitCount() == 0) {
				return null;
			}
			return statistics;
		};
	}

	@Bean
	@StepScope
	public JpaItemWriter<FeedStatistics> makeDailyVisitCountItemWriter() {
		return new JpaItemWriterBuilder<FeedStatistics>()
				.entityManagerFactory(emf)
				.build();
	}
}
