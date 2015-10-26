package com.github.yesql.couchbase.dao.ql;

import com.couchbase.client.java.document.AbstractDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.QueryParams;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryRow;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewRow;
import com.github.yesql.couchbase.dao.ql.CouchbaseQLDao;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.dao.AnimalDao;
import com.google.gson.Gson;
import org.biins.cauchbase.AutoViews;
import org.biins.cauchbase.Bucket;
import org.biins.cauchbase.View;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.couchbase.client.java.query.Query.parameterized;
import static com.couchbase.client.java.query.Query.simple;

/**
 * @author Martin Janys
 */
@Bucket(name = AnimalCouchbaseQLDao.BUCKET_NAME, type = com.couchbase.cbadmin.assets.Bucket.BucketType.COUCHBASE, design = AnimalCouchbaseQLDao.DESIGN_NAME)
public class AnimalCouchbaseQLDao extends CouchbaseQLDao<CouchbaseAnimal> implements AnimalDao<CouchbaseAnimal, String> {

    static final String BUCKET_NAME = "animals";
    static final String DESIGN_NAME = "animals";

    private static final Gson gson = new Gson();
    private static final long SCAN_WAIT_MILLISEC = 600;
    private static final QueryParams QUERY_PARAMS = QueryParams.build().consistency(ScanConsistency.REQUEST_PLUS).scanWait(SCAN_WAIT_MILLISEC, TimeUnit.MILLISECONDS);

    @Value("${couchbase.development.views}")
    private boolean developmentViews;
    @Autowired
    private AutoViews autoViews;

    public AnimalCouchbaseQLDao() {
        super(CouchbaseAnimal.class);
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        autoViews.setup(this);
    }

    public CouchbaseAnimal findEntry(String id) {
        RawJsonDocument document = bucket.get(id, RawJsonDocument.class);
        return gson.fromJson(document.content(), entityType);
    }

    public List<CouchbaseAnimal> findAllEntries() {
        return extractResults(
                bucket.query(simple("SELECT * FROM animals", QUERY_PARAMS)));
    }

    @View(name = "all", map = "classpath:/script/animal/map_all.js")
    public List<String> findAllIds() {
        /** find all (consistent) by view with stale false (stale false force reindex) */
        return bucket.query(ViewQuery.from(DESIGN_NAME, "all").stale(Stale.FALSE).development(developmentViews))
                .allRows()
                .stream()
                .map(ViewRow::document)
                .map(AbstractDocument::id)
                .collect(Collectors.toList());
        /** n1ql provides eventual consistency
        return bucket.query(simple("SELECT id FROM animals"))
                .allRows()
                .stream()
                .map(QueryRow::value)
                .map(jsonObject -> String.valueOf(jsonObject.get("id")))
                .collect(Collectors.toList());
         */
    }

    public String saveEntry(CouchbaseAnimal o) {
        o.setId(uuid());
        RawJsonDocument insert = bucket.insert(RawJsonDocument.create(o.getId(), gson.toJson(o)));
        return insert.id();
    }

    public void updateEntry(CouchbaseAnimal o) {
        RawJsonDocument upsert = bucket.upsert(
                RawJsonDocument.create(o.getId(), gson.toJson(o))
        );
        upsert.content();
    }

    public void deleteEntry(String id) {
        bucket.remove(id);
    }

    public void deleteEntry(CouchbaseAnimal o) {
        deleteEntry(o.getId());
    }

    public int countAll() {
        return bucket.query(simple("SELECT count(*) as count FROM animals", QUERY_PARAMS))
                .allRows()
                .stream()
                .findFirst()
                .orElseGet(() -> null)
                .value()
                .getInt("count");
    }

    public void deleteAll() {
        findAllIds().forEach(this::deleteEntry);
    }

    public List<CouchbaseAnimal> findBySpeciesName(String name) {
        return extractResults(
                bucket.query(parameterized("SELECT * FROM animals where speciesName = $1", JsonArray.from(name))));
    }

    public List<CouchbaseAnimal> findByGenusName(String name) {
        return extractResults(
                bucket.query(parameterized("SELECT * FROM animals where genusName = $1", JsonArray.from(name))));
    }

    public List<CouchbaseAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return extractResults(
                bucket.query(parameterized("SELECT * FROM animals where speciesName = $1 and genusName = $2", JsonArray.from(speciesName, genusName))));
    }

    public List<CouchbaseAnimal> findByWeight(int weight) {
        return extractResults(
                bucket.query(parameterized("SELECT * FROM animals where weight = $1", JsonArray.from(weight))));
    }

    public List<CouchbaseAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return extractResults(
                bucket.query(parameterized("SELECT * FROM animals where weight between $1 and $2", JsonArray.from(startWeight, endWeight))));
    }

    public List<CouchbaseAnimal> findByWeightOrLength(int size) {
        return extractResults(
                bucket.query(parameterized("SELECT * FROM animals where weight = $1 or length = $1", JsonArray.from(size))));
    }

    public List<CouchbaseAnimal> findByArea(String area) {
        return extractResults(
                bucket.query(parameterized("SELECT * FROM animals animals UNNEST areas as area WHERE area = $1", JsonArray.from(area))));
    }

    public List<CouchbaseAnimal> findByAreaIn(String... areas) {
        QueryResult queryResult = bucket.query(parameterized("SELECT * FROM animals animals UNNEST areas as area WHERE area in $1", JsonArray.from(JsonArray.from(areas))));
        if (queryResult.errors() != null && queryResult.errors().size() > 0) {
            throw new RuntimeException(String.valueOf(queryResult.errors()));
        }
        else {
            return queryResult.allRows()
                    .stream()
                    .map(this::extractResult)
                    .distinct()
                    .collect(Collectors.toList());
        }
    }

    protected JsonDocument jsonObjectToDocument(JsonObject jsonObject) {
        return JsonDocument.create(jsonObject.getObject("animals").getString("id"), jsonObject.getObject("animals"));
    }

    protected CouchbaseAnimal jsonObjectToEntity(JsonObject jsonObject) {
        return toEntity(
                jsonObjectToDocument(jsonObject), entityType
        ).content();
    }

    protected CouchbaseAnimal extractResult(QueryRow queryRow) {
        return jsonObjectToEntity(queryRow.value());
    }

    protected List<CouchbaseAnimal> extractResults(QueryResult queryResult) {
        if (queryResult.errors() != null && queryResult.errors().size() > 0) {
            throw new RuntimeException(String.valueOf(queryResult.errors()));
        }
        else {
            return queryResult.allRows()
                    .stream()
                    .map(this::extractResult)
                    .collect(Collectors.toList());
        }
    }

}
