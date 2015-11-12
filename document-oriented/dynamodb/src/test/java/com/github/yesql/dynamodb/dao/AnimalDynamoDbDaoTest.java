package com.github.yesql.dynamodb.dao;

import com.github.yesql.common.test.AnimalDaoIntegrationTest;
import com.github.yesql.dynamodb.model.DynamoDbAnimal;

/**
 * @author Martin Janys
 */
public class AnimalDynamoDbDaoTest extends AnimalDaoIntegrationTest {

    protected AnimalDynamoDbDaoTest() {
        super(DynamoDbAnimal.class);
    }
}