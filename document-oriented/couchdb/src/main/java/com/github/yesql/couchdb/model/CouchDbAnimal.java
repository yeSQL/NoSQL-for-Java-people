package com.github.yesql.couchdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

/**
 * @author Martin Janys
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CouchDbAnimal extends Animal {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String revision;

    public CouchDbAnimal() {
    }

    public CouchDbAnimal(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("revision", revision)
                .add("parent", super.toString())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouchDbAnimal that = (CouchDbAnimal) o;
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

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

}
