package com.github.yesql.couchbase.dao;

import com.couchbase.cbadmin.assets.Bucket.BucketType;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.AsyncViewRow;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.dao.AsyncAnimalDao;
import org.biins.cauchbase.Bucket;
import org.biins.cauchbase.View;
import org.springframework.beans.factory.InitializingBean;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.github.yesql.couchbase.DocumentConverter.convert;

/**
 * @author Martin Janys
 */
@Bucket(name = AnimalCouchbaseAsyncViewDao.BUCKET_NAME, type = BucketType.COUCHBASE, design = AnimalCouchbaseAsyncViewDao.DESIGN_NAME)
public class AnimalCouchbaseAsyncViewDao extends AsyncCouchbaseDao implements AsyncAnimalDao<CouchbaseAnimal, String>, InitializingBean {

    static final String BUCKET_NAME = "animals";
    static final String DESIGN_NAME = "animals";

    public void afterPropertiesSet() throws Exception {
        autoViews.setup(this);
    }

    public Future<CouchbaseAnimal> findEntry(String id) {
        return toFuture(get(id, RawJsonDocument.class));
    }

    @View(name = "all", map = "classpath:/script/animal/map_all.js")
    public Future<List<CouchbaseAnimal>> findAllEntries() {
        return toFutureList(query(viewQueryFrom(DESIGN_NAME, "all").includeDocs(true, RawJsonDocument.class)));
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

    public Future<CouchbaseAnimal> saveEntry(CouchbaseAnimal o) {
        if (o.getId() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        o.setId(uuid());
        return toFuture(insert(convert(o)));
    }

    public Future<CouchbaseAnimal> updateEntry(CouchbaseAnimal o) {
        RawJsonDocument document = convert(o);
        return toFuture(replace(document));
    }

    public Future<Boolean> deleteEntry(String id) {
        try {
            return toFutureBoolean(remove(id));
        }
        catch (DocumentDoesNotExistException ignored) {
        }
        return null;
    }
    public Future<Boolean> deleteEntry(CouchbaseAnimal o) {
        return deleteEntry(o.getId());
    }

    @View(name = "countAll", map = "classpath:/script/animal/map_all.js", reduce = "_count")
    public Future<Integer> countAll() {
        return query(viewQueryFrom(DESIGN_NAME, "countAll"))
                .defaultIfEmpty(null)
                .flatMap(new Func1<AsyncViewResult, Observable<Integer>>() {
                    public Observable<Integer> call(AsyncViewResult result) {
                        return result.rows().first().map(new Func1<AsyncViewRow, Integer>() {
                            public Integer call(AsyncViewRow asyncViewRow) {
                                return (Integer) asyncViewRow.value();
                            }
                        });
                    }
                })
                .defaultIfEmpty(0)
                .timeout(timeout, TimeUnit.SECONDS)
                .toBlocking()
                .toFuture();
    }

    public Future<Boolean> deleteAll() {
        return findAllIds()
                .all(new Func1<String, Boolean>() {
                    public Boolean call(String id) {
                        deleteEntry(id);
                        return true;
                    }
                })
                .defaultIfEmpty(false)
                .timeout(timeout, TimeUnit.SECONDS)
                .toBlocking()
                .toFuture();
    }

    @View(name = "by_name", map = "classpath:/script/animal/map_by_name.js")
    public Future<List<CouchbaseAnimal>> findBySpeciesName(String name) {
        return toFutureList(
                query(viewQueryFrom(DESIGN_NAME, "by_name").key(name).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_genusName", map = "classpath:/script/animal/map_by_genusName.js")
    public Future<List<CouchbaseAnimal>> findByGenusName(String name) {
        return toFutureList(
                query(viewQueryFrom(DESIGN_NAME, "by_genusName").key(name).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_names", map = "classpath:/script/animal/map_by_names.js")
    public Future<List<CouchbaseAnimal>> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return toFutureList(
                query(viewQueryFrom(DESIGN_NAME, "by_names").key(JsonArray.from(speciesName, genusName)).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_weight", map = "classpath:/script/animal/map_by_weight.js")
    public Future<List<CouchbaseAnimal>> findByWeight(int weight) {
        return toFutureList(
                query(viewQueryFrom(DESIGN_NAME, "by_weight").key(weight).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_weight_between", map = "classpath:/script/animal/map_by_weight.js")
    public Future<List<CouchbaseAnimal>> findByWeightBetween(int startWeight, int endWeight) {
        return toFutureList(
                query(viewQueryFrom(DESIGN_NAME, "by_weight_between").startKey(startWeight).endKey(endWeight).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_weight_or_length", map = "classpath:/script/animal/map_by_weight_or_length.js")
    public Future<List<CouchbaseAnimal>> findByWeightOrLength(int size) {
        return toFutureList(
                query(viewQueryFrom(DESIGN_NAME, "by_weight_or_length").key(size).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_area", map = "classpath:/script/animal/map_by_area.js")
    public Future<List<CouchbaseAnimal>> findByArea(String area) {
        return toFutureList(
                query(viewQueryFrom(DESIGN_NAME, "by_area").key(area).includeDocs(true, RawJsonDocument.class)));
    }

    @View(name = "by_areas", map = "classpath:/script/animal/map_by_area.js")
    public Future<List<CouchbaseAnimal>> findByAreaIn(String... area) {
        // todo: uniq document
        return extractFromResult(
                query(viewQueryFrom(DESIGN_NAME, "by_areas").keys(JsonArray.from(area)).includeDocs(true, RawJsonDocument.class)))
                .map(new Func1<List<CouchbaseAnimal>, Set<CouchbaseAnimal>>() {
                    public Set<CouchbaseAnimal> call(List<CouchbaseAnimal> couchbaseAnimals) {
                        return new HashSet<CouchbaseAnimal>(couchbaseAnimals);
                    }
                })
                .map(new Func1<Set<CouchbaseAnimal>, List<CouchbaseAnimal>>() {
                    public List<CouchbaseAnimal> call(Set<CouchbaseAnimal> couchbaseAnimals) {
                        return new ArrayList<CouchbaseAnimal>(couchbaseAnimals);
                    }
                })
                .timeout(timeout, TimeUnit.SECONDS)
                .toBlocking()
                .toFuture();
    }

}
