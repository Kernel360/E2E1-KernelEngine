package com.example.modulebatch.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.modulebatch.job.AddFeedFromBlogJobConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchScheduler {
	private final JobLauncher jobLauncher;
	private final AddFeedFromBlogJobConfig batchConfig;

	@Scheduled(initialDelay = 0, fixedRate = 600000)
	public void runJob() {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {
			jobLauncher.run(batchConfig.addFeedFromBlogJob(), jobParameters);
		} catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | JobParametersInvalidException |
						 JobRestartException e) {
			log.error(String.valueOf(e));
		}
	}
}
