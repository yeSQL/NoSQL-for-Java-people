package com.github.yesql.elasticsearch.dao;

import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import com.github.yesql.elasticsearch.ElasticConfig;
import com.github.yesql.elasticsearch.model.ElasticAnimal;
import org.springframework.test.context.ContextConfiguration;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        classes = ElasticConfig.class
)
public class AnimalElasticSpringDaoTest extends AnimalDaoIntegrationTest {

    public AnimalElasticSpringDaoTest() {
        super(ElasticAnimal.class);
    }
}