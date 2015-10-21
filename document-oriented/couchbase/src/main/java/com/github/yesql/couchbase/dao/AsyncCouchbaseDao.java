package com.github.yesql.couchbase.dao;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.view.*;
import com.github.yesql.couchbase.DocumentConverter;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import org.biins.cauchbase.AutoViews;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Martin Janys
 */
public abstract class AsyncCouchbaseDao {

    @Autowired
    protected AsyncBucket bucket;
    @Autowired
    protected AutoViews autoViews;

    protected Stale stale = Stale.FALSE;
    static final long timeout = 5;

    protected String uuid() {
        return UUID.randomUUID().toString();
    }


    protected Observable<CouchbaseAnimal> extractFromDocument(Observable<RawJsonDocument> observable) {
        return observable
                .map(new Func1<RawJsonDocument, CouchbaseAnimal>() {
                    public CouchbaseAnimal call(RawJsonDocument rawJsonDocument) {
                        return DocumentConverter.convert(rawJsonDocument, CouchbaseAnimal.class);
                    }
                });
    }

    protected Observable<CouchbaseAnimal> extractFromRows(Observable<AsyncViewRow> rows) {
        return rows.flatMap(new Func1<AsyncViewRow, Observable<CouchbaseAnimal>>() {
            public Observable<CouchbaseAnimal> call(AsyncViewRow asyncViewRow) {
                return extractFromDocument(asyncViewRow.document(RawJsonDocument.class));
            }
        });
    }

    protected Observable<List<CouchbaseAnimal>> extractFromResult(Observable<AsyncViewResult> result) {
        return result.flatMap(new Func1<AsyncViewResult, Observable<CouchbaseAnimal>>() {
            public Observable<CouchbaseAnimal> call(AsyncViewResult asyncViewResult) {
                return extractFromRows(asyncViewResult.rows());
            }
        }).toList();
    }

    protected Future<CouchbaseAnimal> toFuture(Observable<RawJsonDocument> observable) {
        return extractFromDocument(observable)
                .timeout(timeout, TimeUnit.SECONDS)
                .toBlocking()
                .toFuture();
    }

    protected Future<List<CouchbaseAnimal>> toFutureList(Observable<AsyncViewResult> asyncViewResultObservable) {
        return extractFromResult(asyncViewResultObservable)
                .timeout(timeout, TimeUnit.SECONDS)
                .toBlocking()
                .toFuture();
    }

    protected Future<Void> toFutureVoid(Observable<?> voidObservable) {
        return voidObservable.first().map(new Func1<Object, Void>() {
            public Void call(Object o) {
                return null;
            }
        }).toBlocking().toFuture();
    }

    protected Future<Boolean> toFutureBoolean(Observable<?> voidObservable) {
        return voidObservable.first().map(new Func1<Object, Boolean>() {
            public Boolean call(Object o) {
                return true;
            }
        }).toBlocking().toFuture();
    }

    /**
     * Shared point for views
     */
    protected ViewQuery viewQueryFrom(String designName, String viewName) {
        ViewQuery viewQuery = ViewQuery.from(designName, viewName).stale(stale);
        if (autoViews.isDevelopmentViews())
            return viewQuery.development();
        else
            return viewQuery;
    }

    protected Observable<AsyncViewResult> query(ViewQuery viewQuery) {
        return bucket.query(viewQuery);
    }

    protected <D extends Document> Observable<D> get(String id, Class<D> documentClass) {
        return bucket.get(id, documentClass);
    }

    protected <D extends Document> Observable<D> insert(D document) {
        return bucket.insert(document);
    }

    protected <D extends Document> Observable<D> replace(D document) {
        return bucket.replace(document);
    }

    protected Observable<JsonDocument> remove(String id) {
        return bucket.remove(id);
    }
}
