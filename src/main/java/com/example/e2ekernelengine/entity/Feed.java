package com.example.e2ekernelengine.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "feed")
public class Feed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_id", columnDefinition = "BIGINT", nullable = false)
	private Long feedId;

	// Blog Entity와 연관관계 섧정
	@ManyToOne
	@JoinColumn(name = "blog_id")
	private Blog blog;

	@Column(name = "feed_url", nullable = false)
	private String feedUrl;

	@Column(name = "feed_title", columnDefinition = "VARCHAR(100)")
	private String feedTitle;

	@Column(name = "feed_detail", nullable = false)
	private String feedDetail;

	@CreationTimestamp
	@Column(name = "feed_created_at", nullable = false)
	private Timestamp feedCreatedAt;
}
