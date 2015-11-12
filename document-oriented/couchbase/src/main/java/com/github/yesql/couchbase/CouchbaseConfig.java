package com.github.yesql.couchbase;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.repository.AsyncRepository;
import com.couchbase.client.java.repository.Repository;
import com.github.yesql.couchbase.dao.sync.AnimalCouchbaseViewDao;
import com.github.yesql.common.PropertyPlaceHolderConfiguration;
import com.github.yesql.common.dao.AnimalDao;
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
@Import({PropertyPlaceHolderConfiguration.class, CouchbaseAutoViewConfig.class})
public class CouchbaseConfig {

    @Value("${couchbase.bucket}") String bucket;
    @Value("${couchbase.nodes}") String[] nodes;
    @Value("${couchbase.password}") String password;
    @Value("${couchbase.timeout.sec}") long timeout;

    @Bean(destroyMethod = "disconnect")
    public Cluster cluster() {
        return CouchbaseCluster.create(nodes);
    }

    @Bean
    public Bucket bucket(Cluster cluster) {
        return cluster.openBucket(bucket, password, 30, TimeUnit.SECONDS);
    }

    @Bean
    public AsyncBucket asyncBucket(Bucket bucket) {
        return bucket.async();
    }

    @Bean
    public Repository repository(Bucket bucket) {
        return bucket.repository();
    }

    @Bean
    public AsyncRepository asyncRepository(Repository repository) {
        return repository.async();
    }

    @Bean
    public AnimalDao animalDao() {
        return new AnimalCouchbaseViewDao();
    }
}
