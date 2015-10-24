package com.github.yesql.couchbase.dao.ql;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.AsyncQueryResult;
import com.couchbase.client.java.query.AsyncQueryRow;
import com.couchbase.client.java.query.Query;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.repository.Repository;
import com.couchbase.client.java.repository.mapping.EntityConverter;
import com.couchbase.client.java.view.Stale;
import com.github.yesql.couchbase.DocumentConverter;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.Identifiable;
import org.biins.cauchbase.AutoViews;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

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
