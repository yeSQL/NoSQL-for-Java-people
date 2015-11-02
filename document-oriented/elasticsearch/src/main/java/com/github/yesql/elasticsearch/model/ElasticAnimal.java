package com.github.yesql.elasticsearch.model;

import com.github.yesql.couchdb.model.Animal;

/**
 * @author Martin Janys
 */
public class ElasticAnimal extends Animal {

    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
