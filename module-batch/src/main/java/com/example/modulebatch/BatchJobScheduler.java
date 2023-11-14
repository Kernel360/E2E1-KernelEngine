package com.example.modulebatch;

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

import com.example.modulebatch.count.job.MakeFeedStatisticsJobConfig;
import com.example.modulebatch.loadData.job.AddFeedFromBlogJobConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchJobScheduler {
	private final JobLauncher jobLauncher;
	private final AddFeedFromBlogJobConfig addFeedFromBlogJobConfig;
	private final MakeFeedStatisticsJobConfig makeFeedStatisticsJobConfig;

	@Scheduled(initialDelay = 0, fixedRate = 6000000)
	// @Scheduled(cron = "0 0 4 * * ?")
	public void runJob() {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {
			jobLauncher.run(addFeedFromBlogJobConfig.addFeedFromDataLoaderJob(), jobParameters);
		} catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | JobParametersInvalidException |
						 JobRestartException e) {
				log.error(String.valueOf(e));
		}
	}
	@Scheduled(initialDelay = 240000, fixedRate = 6000000)
	// @Scheduled(cron = "0 0 3 * * ?")
	public void runMakeDailyStatistics(){
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {
			jobLauncher.run(makeFeedStatisticsJobConfig.makeStatisticsJob(),jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException |
						 JobRestartException e) {
			log.error(String.valueOf(e));
		}
	}

}
