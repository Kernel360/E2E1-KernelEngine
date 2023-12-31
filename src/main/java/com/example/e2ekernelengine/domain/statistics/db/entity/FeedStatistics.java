package com.example.e2ekernelengine.domain.statistics.db.entity;

import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class FeedStatistics {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feedStatId;


	private Long feedId;

	private LocalDateTime statisticsAt;

	private Integer visitCount;

	public static FeedStatistics create(final Feed feed) {
		FeedStatistics statistics = new FeedStatistics();
		statistics.visitCount = feed.getFeedVisitCount();
		statistics.feedId = feed.getFeedId();
		statistics.statisticsAt = LocalDateTime.now().minusDays(1);
		return statistics;
	}
}
