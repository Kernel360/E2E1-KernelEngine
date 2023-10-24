package com.example.e2ekernelengine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	// TODO 블로그 객체 연결 필요
	// @ManyToOne
	// @JoinColumn(name = "blog_id")
	// private Blog blog;

	@Column(name = "post_url")
	private String postUrl;

	@Column(name = "post_title")
	private String postTitle;

	@Column(name = "post_detail")
	private String postDetail;
}