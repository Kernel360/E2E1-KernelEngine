package com.example.e2ekernelengine.global.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.e2ekernelengine.domain.search.repository.EsFeedSearchRepository;
import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepositoryImpl;

@EnableJpaRepositories(basePackages = "com.example.e2ekernelengine.domain.**.db.repository",
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = {EsFeedSearchRepository.class, FeedSearchRepositoryImpl.class}))
@Configuration
public class JpaConfig {

}
