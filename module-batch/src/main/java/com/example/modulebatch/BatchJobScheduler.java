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

import com.example.modulebatch.feed.job.MakeFeedStatisticsJobConfig;
import com.example.modulebatch.loadData.job.AddFeedFromBlogJobConfig;
import com.example.modulebatch.user.job.AddDailyRegisteredUserJobConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchJobScheduler {
	private final JobLauncher jobLauncher;
	private final AddFeedFromBlogJobConfig addFeedFromBlogJobConfig;
	private final MakeFeedStatisticsJobConfig makeFeedStatisticsJobConfig;
	private final AddDailyRegisteredUserJobConfig addDailyRegisteredUserJobConfig;

	// @Scheduled(initialDelay = 300000, fixedRate = 60000000)
	@Scheduled(cron = "0 0 2 * * ?")
	public void runMakeDailyFeedStatistics() {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {
			jobLauncher.run(makeFeedStatisticsJobConfig.makeStatisticsJob(), jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException |
						 JobRestartException e) {
			log.error(String.valueOf(e));
		}
	}

	// @Scheduled(initialDelay = 0, fixedRate = 600000000)
	@Scheduled(cron = "0 0 3 * * ?")
	public void runAddFeedFromDataLoaderJob() {
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

	// @Scheduled(initialDelay = 600000, fixedRate = 300000000)
	@Scheduled(cron = "0 0 4 * * ?")
	public void runMakeUserStatistics() {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {
			jobLauncher.run(addDailyRegisteredUserJobConfig.addDailyRegisteredUserJob(), jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException |
						 JobRestartException e) {
			log.error(String.valueOf(e));
		}
	}

	// TODO:: 오래 지난 조회수 통계 기록 지우기 -> Backlog
	// @Scheduled(initialDelay = 0, fixedRate = 60000000)
	// // @Scheduled(cron = "0 0 3 * * ?")
	// public void runDeleteDailyFeedStatistics() {
	// 	Map<String, JobParameter> confMap = new HashMap<>();
	// 	confMap.put("time", new JobParameter(System.currentTimeMillis()));
	// 	JobParameters jobParameters = new JobParameters(confMap);
	// 	try {
	// 		jobLauncher.run(deleteDeprecatedFeedStatisticsJobConfig.deleteDeprecatedFeedStatisticsJob(), jobParameters);
	// 	} catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException |
	// 					 JobRestartException e) {
	// 		log.error(String.valueOf(e));
	// 	}
	// }

}
