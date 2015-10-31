package com.github.yesql.mongodb.dao;

import com.github.yesql.mongodb.MongoTemplateDbConfig;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        classes = {MongoTemplateDbConfig.class},
        inheritLocations = false
)
public class AnimalMongoTemplateDbDaoTest extends AnimalMongoDbDaoTest {

}