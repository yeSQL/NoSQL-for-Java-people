package com.github.yesql.dynamodb.model;

import com.github.yesql.common.Identifiable;
import com.github.yesql.common.model.Animal;

/**
 * @author Martin Janys
 */
public class DynamoDbAnimal extends Animal implements Identifiable<String> {

    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
