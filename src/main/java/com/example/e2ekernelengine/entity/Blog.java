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

	@Column(name = "blog_url")
	private String url;

	@Column(name = "blog_description")
	private String description;

	@Enumerated(EnumType.STRING)
	private OwnerType ownerType; // attribute converter 로 변경하여야 할까요?

	@Column(name = "blog_lastBuiltDate", nullable = false)
	private Timestamp lastBuiltDate; // lastModified 가 어떨까요?

	@Builder
	public Blog(String rss, String url, String description) {
		this.rss = rss;
		this.url = url;
		this.description = description;
	}

	public void updateBlog(String rss, String url, String description) {
		this.rss = rss;
		this.url = url;
		this.description = description;
	}

}
