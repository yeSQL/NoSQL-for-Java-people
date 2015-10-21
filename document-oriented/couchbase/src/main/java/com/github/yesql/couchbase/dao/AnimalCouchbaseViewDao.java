package com.github.yesql.couchbase.dao;

import com.couchbase.cbadmin.assets.Bucket.BucketType;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.github.yesql.couchbase.DocumentConverter;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.dao.AnimalDao;
import org.biins.cauchbase.Bucket;
import org.biins.cauchbase.View;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.github.yesql.couchbase.DocumentConverter.convert;

/**
 * @author Martin Janys
 */
@Bucket(name = AnimalCouchbaseViewDao.BUCKET_NAME, type = BucketType.COUCHBASE, design = AnimalCouchbaseViewDao.DESIGN_NAME)
public class AnimalCouchbaseViewDao extends CouchbaseDao implements AnimalDao<CouchbaseAnimal, String>, InitializingBean {

    static final String BUCKET_NAME = "animals";
    static final String DESIGN_NAME = "animals";


    public void afterPropertiesSet() throws Exception {
        autoViews.setup(this);
    }

    public CouchbaseAnimal findEntry(String id) {
        RawJsonDocument document = get(id, RawJsonDocument.class);
        return DocumentConverter.convert(document, CouchbaseAnimal.class);
    }

    @View(name = "all", map = "classpath:/script/animal/map_all.js")
    public List<CouchbaseAnimal> findAllEntries() {
        ViewResult all = query(viewQueryFrom(DESIGN_NAME, "all").includeDocs(true, RawJsonDocument.class));
        return convert(all.allRows(), CouchbaseAnimal.class);
    }

    public List<String> findAllIds() {
        ViewResult all = query(viewQueryFrom(DESIGN_NAME, "all"));
        List<String> result = new ArrayList<String>();
        for (ViewRow row : all.allRows()) {
            result.add(row.id());
        }
        return result;
    }

    public CouchbaseAnimal saveEntry(CouchbaseAnimal o) {
        if (o.getId() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        o.setId(uuid());
        RawJsonDocument document = insert(convert(o));
        return convert(document, CouchbaseAnimal.class);
    }

    public CouchbaseAnimal updateEntry(CouchbaseAnimal o) {
        RawJsonDocument document = convert(o);
        document = replace(document);
        return convert(document, CouchbaseAnimal.class);
    }

    public void deleteEntry(String id) {
        try {
            remove(id);
        }
        catch (DocumentDoesNotExistException ignored) {
        }
    }

    public void deleteEntry(CouchbaseAnimal o) {
        deleteEntry(o.getId());
    }

    @View(name = "countAll", map = "classpath:/script/animal/map_all.js", reduce = "_count")
    public int countAll() {
        ViewResult countAll = query(viewQueryFrom(DESIGN_NAME, "countAll"));
        List<ViewRow> rows = countAll.allRows();
        if (rows.size() > 0) {
            return (Integer)(rows.get(0).value());
        }
        else {
            return 0;
        }
    }

    public void deleteAll() {
        List<String> animals = findAllIds();
        for (String id : animals) {
            deleteEntry(id);
        }
    }

    @View(name = "by_name", map = "classpath:/script/animal/map_by_name.js")
    public List<CouchbaseAnimal> findBySpeciesName(String name) {
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_name").key(name).includeDocs(true, RawJsonDocument.class));
        return convert(viewResult.allRows(), CouchbaseAnimal.class);
    }

    @View(name = "by_genusName", map = "classpath:/script/animal/map_by_genusName.js")
    public List<CouchbaseAnimal> findByGenusName(String name) {
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_genusName").key(name).includeDocs(true, RawJsonDocument.class));
        return convert(viewResult.allRows(), CouchbaseAnimal.class);
    }

    @View(name = "by_names", map = "classpath:/script/animal/map_by_names.js")
    public List<CouchbaseAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_names").key(JsonArray.from(speciesName, genusName)).includeDocs(true, RawJsonDocument.class));
        return convert(viewResult.allRows(), CouchbaseAnimal.class);
    }

    @View(name = "by_weight", map = "classpath:/script/animal/map_by_weight.js")
    public List<CouchbaseAnimal> findByWeight(int weight) {
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_weight").key(weight).includeDocs(true, RawJsonDocument.class));
        return convert(viewResult.allRows(), CouchbaseAnimal.class);
    }

    @View(name = "by_weight_between", map = "classpath:/script/animal/map_by_weight.js")
    public List<CouchbaseAnimal> findByWeightBetween(int startWeight, int endWeight) {
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_weight_between").startKey(startWeight).endKey(endWeight).includeDocs(true, RawJsonDocument.class));
        return convert(viewResult.allRows(), CouchbaseAnimal.class);
    }

    @View(name = "by_weight_or_length", map = "classpath:/script/animal/map_by_weight_or_length.js")
    public List<CouchbaseAnimal> findByWeightOrLength(int size) {
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_weight_or_length").key(size).includeDocs(true, RawJsonDocument.class));
        return convert(viewResult.allRows(), CouchbaseAnimal.class);
    }

    @View(name = "by_area", map = "classpath:/script/animal/map_by_area.js")
    public List<CouchbaseAnimal> findByArea(String area) {
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_area").key(area).includeDocs(true, RawJsonDocument.class));
        return convert(viewResult.allRows(), CouchbaseAnimal.class);
    }

    @View(name = "by_areas", map = "classpath:/script/animal/map_by_area.js")
    public List<CouchbaseAnimal> findByAreaIn(String... area) {
        // todo: uniq document
        ViewResult viewResult = query(viewQueryFrom(DESIGN_NAME, "by_areas").keys(JsonArray.from(area)).includeDocs(true, RawJsonDocument.class));
        return new ArrayList<CouchbaseAnimal>(
                new HashSet<CouchbaseAnimal>(
                        convert(viewResult.allRows(), CouchbaseAnimal.class)
                )
        );
    }

}
