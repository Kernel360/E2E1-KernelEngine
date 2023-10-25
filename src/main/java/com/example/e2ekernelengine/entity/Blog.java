package com.example.e2ekernelengine.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "blog")
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blog_id", columnDefinition = "BIGINT", nullable = false)
	private Long blogId;

	// Blog Entity와 연관관계 섧정
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "blog_rss", columnDefinition = "VARCHAR(255)")
	private String blogRss;
	@Column(name = "blog_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String blogUrl;
	@Column(name = "blog_description")
	private String blogDescription;

	@Column(name = "blog_owner_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private BlogOwnerType ownerType;
	@UpdateTimestamp
	@Column(name = "blog_last_build_at", nullable = false)
	private Timestamp blogLastBuildAt;
	@UpdateTimestamp
	@Column(name = "blog_last_crawl_at")
	private Timestamp blogLastCrawlAt;

	public Blog() {
		this.blogLastBuildAt = new Timestamp(System.currentTimeMillis());
		this.blogLastCrawlAt = new Timestamp(System.currentTimeMillis());
	}
}
