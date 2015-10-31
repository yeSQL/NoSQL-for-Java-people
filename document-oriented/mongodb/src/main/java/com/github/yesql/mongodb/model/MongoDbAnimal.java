package com.github.yesql.mongodb.model;

import com.github.yesql.couchdb.model.Animal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Martin Janys
 */
@Document(collection = "animal")
public class MongoDbAnimal extends Animal {

    @Id
    private String id;
    @Version
    private Long version;
    @DBRef
    private Zoo zoo;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Zoo getZoo() {
        return zoo;
    }

    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }
}
