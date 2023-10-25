package com.example.e2ekernelengine.entity;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "blog_description",columnDefinition = "VARCHAR(255)")
    private String description;
    @Column(name = "blog_last_build_date", nullable = false)
    private TimeStamp lastBuildDate;
}
