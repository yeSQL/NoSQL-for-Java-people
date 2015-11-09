package com.github.yesql.dynamodb.dao;

import com.github.yesql.couchdb.model.Animal;
import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import com.github.yesql.dynamodb.model.DynamoDbAnimal;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
public class AnimalDynamoDbDaoTest extends AnimalDaoIntegrationTest {

    protected AnimalDynamoDbDaoTest() {
        super(DynamoDbAnimal.class);
    }
}