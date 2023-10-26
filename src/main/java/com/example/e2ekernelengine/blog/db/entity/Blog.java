package com.example.e2ekernelengine.blog.db.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.e2ekernelengine.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.user.db.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blog_id", columnDefinition = "BIGINT", nullable = false, updatable = false)
	private Long blogId;

	// Blog Entity와 연관관계 섧정
	@OneToOne
	@JoinColumn(name = "user_id", columnDefinition = "BIGINT")
	private User user;

	@Column(name = "blog_writer_name", columnDefinition = "VARCHAR(50)")
	private String blogWriterName;

	@Column(name = "blog_rss_url", columnDefinition = "VARCHAR(255)")
	private String blogRssUrl;

	@Column(name = "blog_url", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
	private String blogUrl;

	@Column(name = "blog_description", columnDefinition = "TEXT")
	private String blogDescription;

	@Column(name = "blog_owner_type", columnDefinition = "VARCHAR(255)", nullable = false)
	@Enumerated(EnumType.STRING)
	private BlogOwnerType blogOwnerType;

	@Column(name = "blog_last_build_at", columnDefinition = "TIMESTAMP", nullable = false)
	private Timestamp blogLastBuildAt;

	@Column(name = "blog_last_crawl_at", columnDefinition = "TIMESTAMP")
	private Timestamp blogLastCrawlAt;

	@Builder
	public Blog(Long blogId, User user, String blogWriterName, String blogRssUrl, String blogUrl, String blogDescription,
			String blogOwnerType, Timestamp blogLastBuildAt, Timestamp blogLastCrawlAt) {
		this.blogId = blogId;
		this.user = user;
		this.blogWriterName = blogWriterName;
		this.blogRssUrl = blogRssUrl;
		this.blogUrl = blogUrl;
		this.blogDescription = blogDescription;
		this.blogOwnerType = BlogOwnerType.valueOf(blogOwnerType);
		Date now = new Date();
		this.blogLastBuildAt = new Timestamp(now.getTime());
		if (blogLastBuildAt == null) {
			this.blogLastBuildAt = new Timestamp(now.getTime());
		} else {
			this.blogLastBuildAt = blogLastBuildAt;
		}
		if (blogLastCrawlAt == null) {
			this.blogLastCrawlAt = new Timestamp(now.getTime());
		} else {
			this.blogLastCrawlAt = blogLastCrawlAt;
		}
	}
}
