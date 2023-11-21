package com.example.e2ekernelengine.domain.statistics.db.repository;

import com.example.e2ekernelengine.domain.statistics.db.entity.UserRegisterStatistics;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRegisterStatisticsRepository extends JpaRepository<UserRegisterStatistics, Long> {


	// TODO: 여기도 querydsl로 바꿔야할듯
	@Query("SELECT urs FROM UserRegisterStatistics urs WHERE urs.statisticsAt BETWEEN :startDate AND :endDate ORDER BY urs.statisticsAt DESC")
	List<UserRegisterStatistics> findAllByStatisticsAtBetweenSort(@Param("startDate") LocalDateTime startDate,
					@Param("endDate") LocalDateTime endDate);
}
