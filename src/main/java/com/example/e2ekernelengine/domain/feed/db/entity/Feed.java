package com.example.e2ekernelengine.domain.feed.db.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.e2ekernelengine.domain.blog.db.entity.Blog;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "feed")
public class Feed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_id", columnDefinition = "BIGINT", nullable = false)
	private Long feedId;

	// Blog Entity와 연관관계 섧정
	@ManyToOne
	@JoinColumn(name = "blog_id", columnDefinition = "BIGINT")
	private Blog blog;

	@Column(name = "feed_url", columnDefinition = "TEXT", nullable = false)
	private String feedUrl;

	@Column(name = "feed_title", columnDefinition = "VARCHAR(255)", nullable = false)
	private String feedTitle;

	@Column(name = "feed_content", columnDefinition = "TEXT", nullable = false)
	private String feedContent;

	@Column(name = "feed_created_at", columnDefinition = "TIMESTAMP", nullable = false)
	private Timestamp feedCreatedAt;

	@Column(name = "feed_description", columnDefinition = "TEXT")
	private String feedDescription;

	@Column(name = "feed_visit_count")

	private Integer feedVisitCount;

	@Builder
	public Feed(Long feedId, Blog blog, String feedUrl, String feedTitle, String feedDescription, String feedContent,
			Timestamp feedCreatedAt, Integer feedVisitCount) {
		this.feedId = feedId;
		this.blog = blog;
		this.feedUrl = feedUrl;
		this.feedTitle = feedTitle;
		this.feedDescription = feedDescription;
		this.feedContent = feedContent;
		this.feedCreatedAt = feedCreatedAt;
		this.feedVisitCount = feedVisitCount;
	}

	public void increaseVisitCount() {
		this.feedVisitCount++;
	}

	public void resetVisitCount() {
		this.feedVisitCount = 0;
	}
}
