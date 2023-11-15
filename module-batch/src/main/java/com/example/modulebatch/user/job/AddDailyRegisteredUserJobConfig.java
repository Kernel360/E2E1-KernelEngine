package com.example.modulebatch.user.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AddDailyRegisteredUserJobConfig {
	private final JobBuilderFactory jbf;
	private final StepBuilderFactory sbf;
	private final AddDailyRegisteredUserTasklet dailyRegisteredUserTasklet;

	@Bean
	public Job addDailyRegisteredUserJob() {
		return jbf.get("addDailyRegisteredUserJob")
				.start(addDailyRegisteredUserStep())
				.build();
	}

	@Bean
	public Step addDailyRegisteredUserStep() {
		return sbf.get("addDailyRegisteredUserStep")
				.tasklet(dailyRegisteredUserTasklet)
				.build();
	}
}
