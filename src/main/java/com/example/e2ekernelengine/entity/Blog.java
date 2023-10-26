package com.example.e2ekernelengine.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	@Column(name = "blog_url", unique = true)
	private String url;

	@Column(name = "blog_description")
	private String description;

	@Enumerated(EnumType.STRING)
	private OwnerType ownerType;

	@Column(name = "blog_lastBuiltDate", nullable = false)
	private Timestamp lastBuildDate;

	@Builder
	public Blog(Long id, String rss, String url, String description, String ownerType) {
		this.id = id;
		this.rss = rss;
		this.url = url;
		this.description = description;
		this.ownerType = OwnerType.valueOf(ownerType);
		Date now = new Date();
		this.lastBuildDate = new Timestamp(now.getTime());
	}
}
