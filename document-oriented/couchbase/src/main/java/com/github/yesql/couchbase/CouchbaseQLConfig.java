package com.github.yesql.couchbase;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.repository.mapping.EntityConverter;
import com.github.yesql.couchbase.dao.AnimalCouchbaseQLDao;
import com.github.yesql.couchdb.PropertyPlaceHolderConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:/couchbase.properties")
@Import({PropertyPlaceHolderConfiguration.class, CouchbaseAutoViewConfig.class})
public class CouchbaseQLConfig extends CouchbaseConfig{

    @Bean
    public AnimalCouchbaseQLDao animalDao() {
        return new AnimalCouchbaseQLDao();
    }

    @Bean
    public EntityConverter<JsonDocument> entityConverter() {
        return new GsonIdentifiableEntityConverter();
    }
}
