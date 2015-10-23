package com.github.yesql.couchbase.sdk;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.github.yesql.couchbase.dao.sdk.sync.AnimalCouchbaseViewDao;
import com.github.yesql.couchdb.Config;
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
public class CouchbaseConfig extends Config {

    @Value("${couchbase.bucket}") String bucket;
    @Value("${couchbase.nodes}") String[] nodes;
    @Value("${couchbase.password}") String password;

    @Bean(destroyMethod = "disconnect")
    public Cluster cluster() {
        return CouchbaseCluster.create(nodes);
    }

    @Bean
    public Bucket bucket(Cluster cluster) {
        return cluster.openBucket(bucket, password, 30, TimeUnit.SECONDS);
    }

    @Bean
    public AnimalCouchbaseViewDao animalDao() {
        return new AnimalCouchbaseViewDao();
    }
}
