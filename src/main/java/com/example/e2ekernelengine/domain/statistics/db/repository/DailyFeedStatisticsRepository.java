package com.example.e2ekernelengine.domain.statistics.db.repository;

import com.example.e2ekernelengine.domain.statistics.db.entity.FeedStatistics;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DailyFeedStatisticsRepository extends JpaRepository<FeedStatistics, Long> {

	// TODO: 진짜..querydsl로 바꿔야할듯
	@Query(value = "SELECT * FROM feed_statistics fs WHERE fs.statistics_at >= :startDate AND fs.statistics_at < :endDate ORDER BY fs.visit_count DESC LIMIT 10", nativeQuery = true)
	List<FeedStatistics> findTop10ByStatisticsAtOrderByVisitCountDesc(
					@Param("startDate") LocalDateTime startDate,
					@Param("endDate") LocalDateTime endDate
	);
}
