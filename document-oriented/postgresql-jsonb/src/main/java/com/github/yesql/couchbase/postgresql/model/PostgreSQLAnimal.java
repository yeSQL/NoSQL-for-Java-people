package com.github.yesql.couchbase.postgresql.model;

import com.github.yesql.couchdb.model.Animal;

/**
 * @author Martin Janys
 */
public class PostgreSQLAnimal extends Animal {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
