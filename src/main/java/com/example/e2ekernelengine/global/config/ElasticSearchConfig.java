package com.example.e2ekernelengine.global.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@EnableElasticsearchRepositories(basePackageClasses = {FeedSearchRepository.class, FeedSearchRepositoryImpl.class})
@EnableElasticsearchRepositories(basePackages = {"com.example.e2ekernelengine.domain.search.repository"})
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

	@Override
	public RestHighLevelClient elasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
						.connectedTo("localhost:9200")
						.build();
		return RestClients.create(clientConfiguration).rest();
	}
}
