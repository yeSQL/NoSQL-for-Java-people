package com.github.yesql.couchbase;

import com.github.yesql.common.dao.*;
import com.github.yesql.couchbase.dao.async.AnimalCouchbaseObservableViewDao;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:/couchbase.properties")
@Import(CouchbaseAutoViewConfig.class)
public class AsyncCouchbaseConfig extends CouchbaseConfig {

    @Bean
    public ObservableAnimalDao<CouchbaseAnimal, String> observableCouchbaseDao() {
        return new AnimalCouchbaseObservableViewDao();
    }

    @Bean
    public AsyncAnimalDao<CouchbaseAnimal, String> asyncAnimalDao() {
        return new ObservableToFutureWrapperDao<>(observableCouchbaseDao(), timeout);
    }

    @Bean
    public AnimalDao<CouchbaseAnimal, String> animalDao() {
        return new FutureToSyncWrapperDao<>(asyncAnimalDao());
    }
}
