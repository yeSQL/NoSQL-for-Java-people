package com.github.yesql.mongodb;

import com.github.yesql.couchdb.PropertyPlaceHolderConfiguration;
import com.github.yesql.mongodb.dao.AnimalMongoDbDao;
import com.github.yesql.mongodb.model.ModelPackage;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import com.github.yesql.mongodb.repository.AnimalMongoDbRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:mongodb.properties")
@Import({PropertyPlaceHolderConfiguration.class})
public class MongoDbConfig extends AbstractMongoConfiguration {

    @Value("${mongodb.db.name}") String databaseName;
    @Value("${mongodb.db.host}") String host;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Bean
    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient(host);
        client.setWriteConcern(WriteConcern.SAFE);
        return client;
    }

    @Override
    protected String getMappingBasePackage() {
        return ModelPackage.class.getPackage().getName();
    }

    @Bean
    public MongoRepositoryFactoryBean<AnimalMongoDbRepository, MongoDbAnimal, String> mongoRepositoryFactoryBean(MongoOperations mongoOperations) {
        MongoRepositoryFactoryBean<AnimalMongoDbRepository, MongoDbAnimal, String> factoryBean = new MongoRepositoryFactoryBean<>();
        factoryBean.setRepositoryInterface(AnimalMongoDbRepository.class);
        factoryBean.setMongoOperations(mongoOperations);
        factoryBean.setCreateIndexesForQueryMethods(true);
        return factoryBean;
    }

    @Bean
    public AnimalMongoDbDao animalMongoDbDao() {
        return new AnimalMongoDbDao();
    }
}
