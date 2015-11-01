package com.github.yesql.elasticsearch.model;

import com.github.yesql.couchdb.model.Animal;

/**
 * @author Martin Janys
 */
public class ElasticAnimal extends Animal {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
