package com.example.e2ekernelengine.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog")
@RequiredArgsConstructor
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

    @Column(name = "blog_lastBuiltDate")
    private LocalDateTime lastBuiltDate; // lastModified 가 어떨까요?

}
