package com.github.yesql.couchbase.dao.async;

import com.couchbase.cbadmin.assets.Bucket.BucketType;
import com.couchbase.client.java.document.AbstractDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.AsyncViewRow;
import com.github.yesql.couchbase.DocumentConverter;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.common.dao.ObservableAnimalDao;
import org.biins.cauchbase.Bucket;
import org.biins.cauchbase.View;
import org.springframework.beans.factory.InitializingBean;
import rx.Observable;
import rx.functions.Func1;

import static com.github.yesql.couchbase.DocumentConverter.convert;

/**
 * @author Martin Janys
 */
@Bucket(name = AnimalCouchbaseObservableViewJ7Dao.BUCKET_NAME, type = BucketType.COUCHBASE, design = AnimalCouchbaseObservableViewJ7Dao.DESIGN_NAME)
public class AnimalCouchbaseObservableViewJ7Dao extends ObservableCouchbaseDao implements ObservableAnimalDao<CouchbaseAnimal, String>, InitializingBean {

    static final String BUCKET_NAME = "animals";
    static final String DESIGN_NAME = "animals";

    public void afterPropertiesSet() throws Exception {
        autoViews.setup(this);
    }

    public Observable<CouchbaseAnimal> findEntry(String id) {
        return convert(get(id, RawJsonDocument.class));
    }

    @View(name = "all", map = "classpath:/script/animal/map_all.js")
    public Observable<CouchbaseAnimal> findAllEntries() {
        return convertViewResult(query(viewQueryFrom(DESIGN_NAME, "all").includeDocs(true, RawJsonDocument.class)));
    }

    public Observable<String> findAllIds() {
        return query(viewQueryFrom(DESIGN_NAME, "all"))
                .flatMap(new Func1<AsyncViewResult, Observable<String>>() {
                    public Observable<String> call(AsyncViewResult result) {
                        return result.rows().map(new Func1<AsyncViewRow, String>() {
                            public String call(AsyncViewRow asyncViewRow) {
                                return asyncViewRow.id();
                            }
                        });
                    }
                });
    }

    public Observable<String> saveEntry(CouchbaseAnimal o) {
        if (o.getId() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        o.setId(uuid());
        Observable<RawJsonDocument> insert = insert(DocumentConverter.convert(o));
        return insert
                .single()
                .map(AbstractDocument::id);
    }

    public Observable<String> updateEntry(CouchbaseAnimal o) {
        RawJsonDocument document = DocumentConverter.convert(o);
        Observable<RawJsonDocument> replace = replace(document);
        return replace
                .single()
                .map(AbstractDocument::id);
    }

    public Observable<Boolean> deleteEntry(String id) {
        return remove(id).defaultIfEmpty(null).map(new Func1<JsonDocument, Boolean>() {
            public Boolean call(JsonDocument jsonDocument) {
                return jsonDocument != null;
            }
        });
    }
    public Observable<Boolean> deleteEntry(CouchbaseAnimal o) {
        return deleteEntry(o.getId());
    }

    @View(name = "countAll", map = "classpath:/script/animal/map_all.js", reduce = "_count")
    public Observable<Integer> countAll() {
        return query(viewQueryFrom(DESIGN_NAME, "countAll"))
                .flatMap(new Func1<AsyncViewResult, Observable<Integer>>() {
                    public Observable<Integer> call(AsyncViewResult result) {
                        return result.rows().defaultIfEmpty(null).map(new Func1<AsyncViewRow, Integer>() {
                            public Integer call(AsyncViewRow asyncViewRow) {
                                return asyncViewRow != null ? (Integer) asyncViewRow.value() : 0;
                            }
                        });
                    }
                })
                .defaultIfEmpty(0);
    }

    public Observable<Boolean> deleteAll() {
        return findAllIds()
                .flatMap(new Func1<String, Observable<JsonDocument>>() {
                    public Observable<JsonDocument> call(String id) {
                        return bucket.remove(id);
                    }
                })
                .defaultIfEmpty(null)
                .all(new Func1<JsonDocument, Boolean>() {
                    public Boolean call(JsonDocument jsonDocument) {
                        return jsonDocument != null;
                    }
                })
                .defaultIfEmpty(false);
    }

    @View(name = "by_name", map = "classpath:/script/animal/map_by_name.js")
    public Observable<CouchbaseAnimal> findBySpeciesName(String name) {
        return convertViewResult(
                query(viewQueryFrom(DESIGN_NAME, "by_name").key(name).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_genusName", map = "classpath:/script/animal/map_by_genusName.js")
    public Observable<CouchbaseAnimal> findByGenusName(String name) {
        return convertViewResult(
                query(viewQueryFrom(DESIGN_NAME, "by_genusName").key(name).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_names", map = "classpath:/script/animal/map_by_names.js")
    public Observable<CouchbaseAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return convertViewResult(
                query(viewQueryFrom(DESIGN_NAME, "by_names").key(JsonArray.from(speciesName, genusName)).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_weight", map = "classpath:/script/animal/map_by_weight.js")
    public Observable<CouchbaseAnimal> findByWeight(int weight) {
        return convertViewResult(
                query(viewQueryFrom(DESIGN_NAME, "by_weight").key(weight).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_weight_between", map = "classpath:/script/animal/map_by_weight.js")
    public Observable<CouchbaseAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return convertViewResult(
                query(viewQueryFrom(DESIGN_NAME, "by_weight_between").startKey(startWeight).endKey(endWeight).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_weight_or_length", map = "classpath:/script/animal/map_by_weight_or_length.js")
    public Observable<CouchbaseAnimal> findByWeightOrLength(int size) {
        return convertViewResult(
                query(viewQueryFrom(DESIGN_NAME, "by_weight_or_length").key(size).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_area", map = "classpath:/script/animal/map_by_area.js")
    public Observable<CouchbaseAnimal> findByArea(String area) {
        return convertViewResult(
                query(viewQueryFrom(DESIGN_NAME, "by_area").key(area).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_areas", map = "classpath:/script/animal/map_by_area.js")
    public Observable<CouchbaseAnimal> findByAreaIn(String... area) {
        return extractFromResult(
                query(viewQueryFrom(DESIGN_NAME, "by_areas").keys(JsonArray.from(area)).includeDocs(true, RawJsonDocument.class)))
                .distinct(new Func1<CouchbaseAnimal, String>() {
                    public String call(CouchbaseAnimal couchbaseAnimal) {
                        return couchbaseAnimal.getId();
                    }
                });
    }

}
