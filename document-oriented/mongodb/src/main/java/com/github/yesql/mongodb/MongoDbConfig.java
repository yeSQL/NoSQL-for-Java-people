package com.github.yesql.mongodb;

import com.github.yesql.common.PropertyPlaceHolderConfiguration;
import com.github.yesql.common.dao.AnimalDao;
import com.github.yesql.mongodb.dao.AnimalMongoDbDao;
import com.github.yesql.mongodb.model.ModelPackage;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import com.github.yesql.mongodb.model.Zoo;
import com.github.yesql.mongodb.repository.AnimalMongoDbRepository;
import com.github.yesql.mongodb.repository.ZooRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
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
        return new MongoClient(host);
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
    public MongoRepositoryFactoryBean<ZooRepository, Zoo, String> zooRepositoryRepositoryFactoryBean(MongoOperations mongoOperations) {
        MongoRepositoryFactoryBean<ZooRepository, Zoo, String> factoryBean = new MongoRepositoryFactoryBean<>();
        factoryBean.setRepositoryInterface(ZooRepository.class);
        factoryBean.setMongoOperations(mongoOperations);
        factoryBean.setCreateIndexesForQueryMethods(true);
        return factoryBean;
    }

    @Bean
    public AnimalDao<MongoDbAnimal, String> animalMongoDbDao() {
        return new AnimalMongoDbDao();
    }

    @Bean
    public LoggingEventListener mappingEventsListener() {
        return new LoggingEventListener();
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }
}
