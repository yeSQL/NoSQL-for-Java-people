package com.github.yesql.couchbase.model;

import com.github.yesql.couchdb.Identifiable;
import com.github.yesql.couchdb.model.Animal;
import com.google.common.base.Objects;

/**
 * @author Martin Janys
 */
public class CouchbaseAnimal extends Animal implements Identifiable<String> {

    private String id;

    public CouchbaseAnimal() {
    }

    public CouchbaseAnimal(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("parent", super.toString())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouchbaseAnimal that = (CouchbaseAnimal) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
