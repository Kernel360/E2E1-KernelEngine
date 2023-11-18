package com.example.e2ekernelengine.global.config;

import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepository;
import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepositoryImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.e2ekernelengine.domain.**.db.repository",
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
								classes = {FeedSearchRepository.class, FeedSearchRepositoryImpl.class}))
@Configuration
public class JpaConfig {

}
