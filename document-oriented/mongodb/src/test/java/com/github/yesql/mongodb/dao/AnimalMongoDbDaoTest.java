package com.github.yesql.mongodb.dao;

import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import com.github.yesql.mongodb.MongoDbConfig;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        classes = {MongoDbConfig.class}
)
public class AnimalMongoDbDaoTest extends AnimalDaoIntegrationTest {

    protected AnimalMongoDbDaoTest() {
        super(MongoDbAnimal.class);
    }
}