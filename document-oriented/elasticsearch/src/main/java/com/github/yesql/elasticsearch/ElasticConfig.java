package com.github.yesql.elasticsearch;

import com.github.yesql.couchdb.PropertyPlaceHolderConfiguration;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Martin Janys
 */
@Configuration
@Import({PropertyPlaceHolderConfiguration.class})
@EnableElasticsearchRepositories(basePackages = "com.github.yesql.elasticsearch.repository")
public class ElasticConfig {
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(NodeBuilder.nodeBuilder().local(true).node().client());
    }
}
