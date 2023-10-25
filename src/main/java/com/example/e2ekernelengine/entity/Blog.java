package com.example.e2ekernelengine.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blog_id")
	private Long id;

	@Column(name = "blog_rss", columnDefinition = "VARCHAR(255)")
	private String rss;

	@Column(name = "blog_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String url;

	@Column(name = "blog_description", columnDefinition = "VARCHAR(255)")
	private String description;

	@Column(name = "blog_last_build_date", nullable = false)
	private Timestamp lastBuildDate;
}
