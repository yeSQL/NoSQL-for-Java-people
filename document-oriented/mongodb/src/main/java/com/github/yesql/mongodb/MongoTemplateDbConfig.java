package com.github.yesql.mongodb;

import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.mongodb.dao.AnimalMongoTemplateDao;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Janys
 */
@Configuration
public class MongoTemplateDbConfig extends MongoDbConfig {

    @Bean
    public AnimalDao<MongoDbAnimal, String> animalMongoDbDao() {
        return new AnimalMongoTemplateDao();
    }

}
