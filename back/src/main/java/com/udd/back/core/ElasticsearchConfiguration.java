package com.udd.back.core;

import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.udd.back.index")
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

        @Value("${elasticsearch.host}")
        private String host;

        @Value("${elasticsearch.port}")
        private int port;

        @Value("${elasticsearch.username}")
        private String userName;

        @Value("${elasticsearch.password}")
        private String password;

        @NotNull
        @Override
        public RestHighLevelClient elasticsearchClient() {
                ClientConfiguration config = ClientConfiguration.builder()
                        .connectedTo(host + ":" + port)
                        .withBasicAuth(userName, password)
                        .build();

                return RestClients.create(config).rest();
        }

}
