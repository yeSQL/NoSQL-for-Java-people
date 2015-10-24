package com.github.yesql.couchbase.dao.async;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.AsyncViewRow;
import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;
import com.github.yesql.couchbase.DocumentConverter;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import org.biins.cauchbase.AutoViews;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author Martin Janys
 */
public abstract class ObservableCouchbaseDao {

    @Autowired
    protected AsyncBucket bucket;
    @Autowired
    protected AutoViews autoViews;

    protected Stale stale = Stale.FALSE;

    protected String uuid() {
        return UUID.randomUUID().toString();
    }

    protected Observable<CouchbaseAnimal> convert(Observable<RawJsonDocument> observable) {
        return observable
                .map(rawJsonDocument -> DocumentConverter.convert(rawJsonDocument, CouchbaseAnimal.class));
    }

    protected Observable<List<CouchbaseAnimal>> convertList(Observable<AsyncViewResult> observable) {
        return extractFromResult(observable)
                .distinct(couchbaseAnimal -> couchbaseAnimal.getId())
                .toList();
    }

    protected Observable<CouchbaseAnimal> extractFromDocument(Observable<RawJsonDocument> observable) {
        return observable
                .map(rawJsonDocument -> DocumentConverter.convert(rawJsonDocument, CouchbaseAnimal.class));
    }

    protected Observable<CouchbaseAnimal> extractFromRows(Observable<AsyncViewRow> rows) {
        return rows
                .flatMap(asyncViewRow -> extractFromDocument(asyncViewRow.document(RawJsonDocument.class)));
    }

    protected Observable<CouchbaseAnimal> extractFromResult(Observable<AsyncViewResult> result) {
        return result
                .flatMap(asyncViewResult -> extractFromRows(asyncViewResult.rows()));
    }

    protected Future<Void> toFutureVoid(Observable<?> voidObservable) {
        return voidObservable
                .first()
                .map(o -> (Void) null)
                .toBlocking()
                .toFuture();
    }

    protected Future<Boolean> toFutureBoolean(Observable<?> voidObservable) {
        return voidObservable
                .first()
                .map(o -> true)
                .toBlocking()
                .toFuture();
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
