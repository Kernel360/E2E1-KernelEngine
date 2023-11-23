package com.example.e2ekernelengine.domain.blog.db.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.e2ekernelengine.domain.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.domain.feed.db.entity.Feed;
import com.example.e2ekernelengine.domain.user.db.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Blog {

	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Feed> feedList = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blog_id", columnDefinition = "BIGINT", nullable = false, updatable = false)
	private Long blogId;

	@OneToOne
	@JoinColumn(name = "user_id", columnDefinition = "BIGINT")
	private User user;

	@Column(name = "blog_title", columnDefinition = "VARCHAR(255)", nullable = false)
	private String blogTitle;

	@Column(name = "blog_writer_name", columnDefinition = "VARCHAR(50)")
	private String blogWriterName;

	@Column(name = "blog_rss_url", columnDefinition = "TEXT", nullable = false, unique = true)
	private String blogRssUrl;

	@Column(name = "blog_url", columnDefinition = "TEXT", unique = true)
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
	public Blog(Long blogId, User user, String blogTitle, String blogWriterName, String blogRssUrl, String blogUrl,
			String blogDescription, String blogOwnerType, Timestamp blogLastBuildAt, Timestamp blogLastCrawlAt) {
		this.blogId = blogId;
		this.user = user;
		this.blogTitle = blogTitle;
		this.blogWriterName = blogWriterName;
		this.blogRssUrl = blogRssUrl;
		this.blogUrl = blogUrl;
		this.blogDescription = blogDescription;
		this.blogOwnerType = BlogOwnerType.valueOf(blogOwnerType);
		Date now = new Date();
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

	public static Blog of(User user, String blogTitle, String blogWriterName, String blogRssUrl, String blogUrl,
			String blogDescription, BlogOwnerType blogOwnerType, Timestamp blogLastBuildAt, Timestamp blogLastCrawlAt) {
		return Blog.builder()
				.user(user)
				.blogTitle(blogTitle)
				.blogWriterName(blogWriterName)
				.blogRssUrl(blogRssUrl)
				.blogUrl(blogUrl)
				.blogDescription(blogDescription)
				.blogOwnerType(blogOwnerType.toString())
				.blogLastBuildAt(blogLastBuildAt)
				.blogLastCrawlAt(blogLastCrawlAt)
				.build();
	}

	public static Blog of(Long blogId, User user, String blogTitle, String blogWriterName, String blogRssUrl,
			String blogUrl, String blogDescription, BlogOwnerType blogOwnerType, Timestamp blogLastBuildAt,
			Timestamp blogLastCrawlAt) {
		return Blog.builder()
				.blogId(blogId)
				.user(user)
				.blogTitle(blogTitle)
				.blogWriterName(blogWriterName)
				.blogRssUrl(blogRssUrl)
				.blogUrl(blogUrl)
				.blogDescription(blogDescription)
				.blogOwnerType(blogOwnerType.toString())
				.blogLastBuildAt(blogLastBuildAt)
				.blogLastCrawlAt(blogLastCrawlAt)
				.build();
	}

	public void updateLastCrawlAt() {
		this.blogLastCrawlAt = new Timestamp(new Date().getTime());
	}
}
