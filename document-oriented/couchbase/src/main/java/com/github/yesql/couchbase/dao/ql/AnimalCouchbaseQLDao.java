package com.github.yesql.couchbase.dao.ql;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.QueryParams;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryRow;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.github.yesql.couchbase.dao.ql.CouchbaseQLDao;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.dao.AnimalDao;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.couchbase.client.java.query.Query.parameterized;
import static com.couchbase.client.java.query.Query.simple;

/**
 * @author Martin Janys
 */
public class AnimalCouchbaseQLDao extends CouchbaseQLDao<CouchbaseAnimal> implements AnimalDao<CouchbaseAnimal, String> {

    private static final Gson gson = new Gson();
    private static final long SCAN_WAIT_MILLISEC = 600;
    private static final QueryParams QUERY_PARAMS = QueryParams.build().consistency(ScanConsistency.REQUEST_PLUS).scanWait(SCAN_WAIT_MILLISEC, TimeUnit.MILLISECONDS);

    public AnimalCouchbaseQLDao() {
        super(CouchbaseAnimal.class);
    }

    public CouchbaseAnimal findEntry(String id) {
        RawJsonDocument document = bucket.get(id, RawJsonDocument.class);
        return gson.fromJson(document.content(), entityType);
    }

    public List<CouchbaseAnimal> findAllEntries() {
        return extractResults(
                bucket.query(simple("SELECT * FROM animals", QUERY_PARAMS)));
    }

    public List<String> findAllIds() {
        return bucket.query(simple("SELECT id FROM animals"))
                .allRows()
                .stream()
                .map(QueryRow::value)
                .map(jsonObject -> String.valueOf(jsonObject.get("id")))
                .collect(Collectors.toList());
    }

    public CouchbaseAnimal saveEntry(CouchbaseAnimal o) {
        o.setId(uuid());
        RawJsonDocument insert = bucket.insert(RawJsonDocument.create(o.getId(), gson.toJson(o)));
        return gson.fromJson(insert.content(), entityType);
    }

    public CouchbaseAnimal updateEntry(CouchbaseAnimal o) {
        RawJsonDocument upsert = bucket.upsert(
                RawJsonDocument.create(o.getId(), gson.toJson(o))
        );
        return gson.fromJson(upsert.content(), entityType);
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
