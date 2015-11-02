package com.github.yesql.elasticsearch;

import com.github.yesql.couchdb.PropertyPlaceHolderConfiguration;
import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.couchdb.dao.AsyncAnimalDao;
import com.github.yesql.couchdb.dao.FutureToSyncWrapperDao;
import com.github.yesql.elasticsearch.dao.AsyncAnimalElasticDaoImpl;
import com.github.yesql.elasticsearch.model.ElasticAnimal;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:elasticsearch.properties")
@Import({PropertyPlaceHolderConfiguration.class})
public class ElasticConfig {

    @Bean(destroyMethod = "close")
    public Client client() throws UnknownHostException {
        return TransportClient.builder()
                .settings(Settings.builder().put("cluster.name", "elastic"))
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    @Bean
    public AsyncAnimalDao<ElasticAnimal, String> asyncElasticAnimalAnimalDao() {
        return new AsyncAnimalElasticDaoImpl();
    }

    @Bean
    public AnimalDao<ElasticAnimal, String> elasticAnimalAnimalDao(AsyncAnimalDao<ElasticAnimal, String> dao) {
        return new FutureToSyncWrapperDao<>(dao);
    }
}
