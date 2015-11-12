package com.github.yesql.couchbase.dao.ql;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.repository.Repository;
import com.couchbase.client.java.repository.mapping.EntityConverter;
import com.github.yesql.common.Identifiable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author Martin Janys
 */
public abstract class CouchbaseQLDao<E> {

    protected final Class<E> entityType;

    public CouchbaseQLDao(Class<E> entityType) {
        this.entityType = entityType;
    }

    @Autowired
    protected AsyncBucket asyncBucket;
    @Autowired
    protected Bucket bucket;
    @Autowired
    protected Repository repository;
    @Autowired
    protected EntityConverter<JsonDocument> converter;

    protected String uuid() {
        return UUID.randomUUID().toString();
    }

    protected <T> EntityDocument<T> toEntity(JsonDocument jsonDocument, Class<T> entityType) {
        return converter.toEntity(jsonDocument, entityType);
    }

    protected <T extends Identifiable<String>> EntityDocument<T> toEntity(T obj, Class<T> entityType) {
        return converter.toEntity(fromEntity(obj.getId(), obj), entityType);
    }

    protected <T> JsonDocument fromEntity(String id, T obj) {
        return converter.fromEntity(EntityDocument.create(id, obj));
    }

    protected <T> JsonDocument fromEntity(T obj) {
        return converter.fromEntity(EntityDocument.create(obj));
    }


}
