package com.example.e2ekernelengine.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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

	@Column(name = "blog_rss", nullable = false)
	private String rss;

	@Column(name = "blog_url", unique = true) // unique로 둬야 한다...
	private String url;

	@Column(name = "blog_description")
	private String description;

	@Enumerated(EnumType.STRING)
	private OwnerType ownerType; // attribute converter 로 변경하여야 할까요?

	@Column(name = "blog_lastModified", nullable = false)
	@CreationTimestamp
	private Timestamp lastModified;

	@Builder
	public Blog(String rss, String url, String description, String ownerType) {
		this.rss = rss;
		this.url = url;
		this.description = description;
		this.ownerType = OwnerType.valueOf(ownerType);
	}

	public void updateBlog(String rss, String url, String description) {
		this.rss = rss;
		this.url = url;
		this.description = description;
	}
}
