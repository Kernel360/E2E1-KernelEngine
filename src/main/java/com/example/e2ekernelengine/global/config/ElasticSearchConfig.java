package com.example.e2ekernelengine.domain.search.config;

import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepository;
import com.example.e2ekernelengine.domain.search.repository.FeedSearchRepositoryImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackageClasses = {FeedSearchRepository.class, FeedSearchRepositoryImpl.class})
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
