package com.example.modulebatch.count.repository;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.e2ekernelengine.domain.feed.db.entity.Feed;

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
	private LocalDateTime createdAt;

	private Integer accessCount;

	public static FeedStatistics create(final Feed feed) {
		FeedStatistics statistics = new FeedStatistics();
		statistics.accessCount = feed.getAccessCount();
		statistics.feedId = feed.getFeedId();
		statistics.createdAt = LocalDateTime.now().minusDays(1);
		return statistics;
	}
}