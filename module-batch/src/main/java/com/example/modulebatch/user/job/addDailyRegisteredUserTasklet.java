package com.example.modulebatch.user.job;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.example.modulebatch.user.db.repository.UserStatisticsRepository;
import com.example.modulebatch.user.db.entity.UserStatistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@StepScope
@RequiredArgsConstructor
public class addDailyRegisteredUserTasklet implements Tasklet {
	private final UserStatisticsRepository userStatisticsRepository;
	private final UserRepository userRepository;

	@Transactional
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now()/*.minusDays(1)*/, LocalTime.of(0, 0, 0));
		LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now()/*.minusDays(1)*/, LocalTime.of(23, 59, 59));

		List<User> registeredLastDay = userRepository.findAllByUserRegisteredAtBetween(Timestamp.valueOf(startDatetime),
				Timestamp.valueOf(endDatetime));
		userStatisticsRepository.save(UserStatistics.create(registeredLastDay.size()));
		return RepeatStatus.FINISHED;
	}
}
