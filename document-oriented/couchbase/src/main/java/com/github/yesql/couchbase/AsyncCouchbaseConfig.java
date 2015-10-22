package com.github.yesql.couchbase;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.github.yesql.couchbase.dao.AnimalCouchbaseAsyncViewDao;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.Config;
import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.couchdb.dao.FutureToSyncWrapperDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:/couchbase.properties")
@Import(CouchbaseAutoViewConfig.class)
public class AsyncCouchbaseConfig extends Config {

    @Value("${couchbase.bucket}") String bucket;
    @Value("${couchbase.nodes}") String[] nodes;
    @Value("${couchbase.password}") String password;

    @Bean(destroyMethod = "disconnect")
    public Cluster cluster() {
        return CouchbaseCluster.create(nodes);
    }

    @Bean
    public AsyncBucket bucket(Cluster cluster) {
        return cluster.openBucket(bucket, password, 30, TimeUnit.SECONDS).async();
    }

    @Bean
    public AnimalCouchbaseAsyncViewDao animalAsyncDao() {
        return new AnimalCouchbaseAsyncViewDao();
    }

    @Bean
    public AnimalDao<CouchbaseAnimal, String> animalDao(AnimalCouchbaseAsyncViewDao asyncViewDao) {
        return new FutureToSyncWrapperDao<CouchbaseAnimal, String>(asyncViewDao);
    }
}
