package com.example.modulebatch.user.job;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.example.e2ekernelengine.domain.user.db.entity.User;
import com.example.e2ekernelengine.domain.user.db.repository.UserRepository;
import com.example.modulebatch.user.db.entity.UserRegisterStatistics;
import com.example.modulebatch.user.db.repository.UserRegisterStatisticsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@StepScope
@RequiredArgsConstructor
public class AddDailyRegisteredUserTasklet implements Tasklet {
	private final UserRegisterStatisticsRepository userRegisterStatisticsRepository;
	private final UserRepository userRepository;

	@Transactional
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Timestamp startDatetime = Timestamp.valueOf(LocalDate.now().atStartOfDay());
		Timestamp endDatetime = Timestamp.valueOf(LocalDate.now().atTime(23, 59, 59));
		List<User> registeredLastDay = userRepository.findAllByUserRegisteredAtBetween(startDatetime, endDatetime);
		userRegisterStatisticsRepository.save(UserRegisterStatistics.create(registeredLastDay.size()));
		return RepeatStatus.FINISHED;
	}
}
