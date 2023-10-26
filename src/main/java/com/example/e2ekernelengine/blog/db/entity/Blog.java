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
import javax.persistence.ManyToOne;
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
	@Column(name = "blog_id", nullable = false, updatable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "blog_writer_name")
	private String blogWriterName;

	@Column(name = "blog_rss", nullable = false)
	private String rss;

	@Column(name = "blog_url", unique = true)
	private String url;

	@Column(name = "blog_description")
	private String description;

	@Enumerated(EnumType.STRING)
	private BlogOwnerType ownerType;

	@Column(name = "blog_last_build_date", nullable = false)
	private Timestamp lastBuildDate;

	@Column(name = "blog_last_crawl_date", nullable = false)
	private Timestamp lastCrawlDate;

	@Builder
	public Blog(Long id, User user, String blogWriterName, String rss, String url, String description, String ownerType,
			Timestamp lastBuildDate, Timestamp lastCrawlDate) {
		this.id = id;
		this.user = user;
		this.blogWriterName = blogWriterName;
		this.rss = rss;
		this.url = url;
		this.description = description;
		this.ownerType = BlogOwnerType.valueOf(ownerType);
		Date now = new Date();
		if (lastBuildDate == null) {
			this.lastBuildDate = new Timestamp(now.getTime());
		} else {
			this.lastBuildDate = lastBuildDate;
		}
		if (lastCrawlDate == null) {
			this.lastCrawlDate = new Timestamp(now.getTime());
		} else {
			this.lastCrawlDate = lastCrawlDate;
		}
	}
}
