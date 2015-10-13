package com.github.yesql.couchdb.dao;

import com.github.yesql.couchdb.model.CouchDbAnimal;
import org.ektorp.BulkDeleteDocument;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

/**
 * @author Martin Janys
 */
@View(name = "all", map = "classpath:/script/animal/map_all.js")
public class AnimalCouchDbDao extends CouchDbRepositorySupport<CouchDbAnimal> implements AnimalDao<CouchDbAnimal, String>, InitializingBean{

    public AnimalCouchDbDao(CouchDbConnector db) {
        super(CouchDbAnimal.class, db);
    }

    public void afterPropertiesSet() throws Exception {
        /* auto generation views */
        initStandardDesignDocument();
    }

    public List<CouchDbAnimal> findAllEntries() {
        return getAll();
    }

    public CouchDbAnimal findEntry(String id) {
        return get(id);
    }

    public CouchDbAnimal saveEntry(CouchDbAnimal animal) {
        /**
         * ! You should do UUID in app (prefer than in DB) this prevents double entry creation if DB fails
         */
        animal.setId(UUID.randomUUID().toString());
        add(animal);
        return animal;
    }

    public void update(CouchDbAnimal animal) {
        super.update(animal);
    }

    public CouchDbAnimal updateEntry(CouchDbAnimal animal) {
        update(animal);
        return animal;
    }

    public void deleteEntry(String id) {
        remove(get(id));
    }

    public void deleteEntry(CouchDbAnimal animal) {
        remove(animal);
    }

    public void deleteAll() {
        List<CouchDbAnimal> allDoc = findAllEntries();
        List<Object> bulkDocs = new ArrayList<Object>();

        for (CouchDbAnimal doc : allDoc) {
            bulkDocs.add(BulkDeleteDocument.of(doc));
        }

        db.executeBulk(bulkDocs);
    }

    /**
     * Emits key, doc. Doc is stored on disk (multiple times)
     */
    @View(name = "by_name", map = "classpath:/script/animal/map_by_name.js")
    public List<CouchDbAnimal> findBySpeciesName(String name) {
         return db.queryView(createQuery("by_name").key(name), CouchDbAnimal.class);
    }

    /**
     * @GenerateView emits only key & doc._id, document is included with q.includeDocs(true)
     */
    @GenerateView
    // @View(name = "by_genusName", map = "classpath:/script/animal/map_by_genusName.js")
    public List<CouchDbAnimal> findByGenusName(String name) {
        return db.queryView(createQuery("by_genusName").key(name).includeDocs(true), CouchDbAnimal.class);
    }

    @View(name = "by_names", map = "classpath:/script/animal/map_by_names.js")
    public List<CouchDbAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return db.queryView(createQuery("by_names").key(ComplexKey.of(speciesName, genusName)).includeDocs(true), CouchDbAnimal.class);
    }

    @View(name = "by_weight", map = "classpath:/script/animal/map_by_weight.js")
    public List<CouchDbAnimal> findByWeight(int weight) {
        return db.queryView(createQuery("by_weight").key(weight).includeDocs(true), CouchDbAnimal.class);
    }

    @View(name = "by_weight_between", map = "classpath:/script/animal/map_by_weight.js")
    public List<CouchDbAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return db.queryView(createQuery("by_weight_between").startKey(startWeight).endKey(endWeight).includeDocs(true), CouchDbAnimal.class);
    }

    @View(name = "by_weight_or_length", map = "classpath:/script/animal/map_by_weight_or_length.js")
    public List<CouchDbAnimal> findByWeightOrLength(int size) {
        return db.queryView(createQuery("by_weight_or_length").key(size).includeDocs(true), CouchDbAnimal.class);
    }

    @View(name = "by_area", map = "classpath:/script/animal/map_by_area.js")
    public List<CouchDbAnimal> findByArea(String area) {
        return db.queryView(createQuery("by_area").key(area).includeDocs(true), CouchDbAnimal.class);
    }

    @View(name = "by_areas", map = "classpath:/script/animal/map_by_area.js")
    public List<CouchDbAnimal> findByAreaIn(String... area) {
        return new ArrayList<CouchDbAnimal>(
                new HashSet<CouchDbAnimal>(
                        db.queryView(createQuery("by_areas").keys(Arrays.asList(area)).includeDocs(true), CouchDbAnimal.class)
                )
        );
    }

    @View(name = "count_all", map = "classpath:/script/animal/map_all.js", reduce = "_count")
    // @View(name = "count_all", map = "classpath:/script/animal/map_all.js", reduce = "classpath:/script/animal/reduce_count.js")
    public int countAll() {
        List<ViewResult.Row> countAll = db.queryView(createQuery("count_all")).getRows();
        if (countAll.size() > 0) {
            return countAll.get(0).getValueAsInt();
        }
        else {
            return 0;
        }
    }
}
