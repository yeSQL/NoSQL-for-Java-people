package com.github.yesql.couchbase.dao.sync;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import org.biins.cauchbase.AutoViews;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author Martin Janys
 */
public abstract class CouchbaseDao {

    @Autowired
    protected Bucket bucket;
    @Autowired
    protected AutoViews autoViews;

    protected Stale stale = Stale.FALSE;

    protected String uuid() {
        return UUID.randomUUID().toString();
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

    protected ViewResult query(ViewQuery viewQuery) {
        return bucket.query(viewQuery);
    }

    protected  <D extends Document> D get(String id, Class<D> documentClass) {
        return bucket.get(id, documentClass);
    }

    protected <D extends Document> D insert(D document) {
        return bucket.insert(document);
    }

    protected <D extends Document> D replace(D document) {
        return bucket.replace(document);
    }

    protected void remove(String id) {
        bucket.remove(id);
    }
}
