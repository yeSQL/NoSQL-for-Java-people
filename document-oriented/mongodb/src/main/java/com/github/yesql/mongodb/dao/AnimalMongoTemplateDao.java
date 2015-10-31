package com.github.yesql.mongodb.dao;

import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

/**
 * @author Martin Janys
 */
public class AnimalMongoTemplateDao implements AnimalDao<MongoDbAnimal, String> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MongoDbAnimal findEntry(String s) {
        return mongoTemplate.findById(s, MongoDbAnimal.class);
    }

    @Override
    public List<MongoDbAnimal> findAllEntries() {
        return mongoTemplate.findAll(MongoDbAnimal.class);
    }

    @Override
    public String saveEntry(MongoDbAnimal animal) {
        Assert.isNull(animal.getId());
        mongoTemplate.insert(animal);
        return animal.getId();
    }

    @Override
    public void updateEntry(MongoDbAnimal animal) {
        Assert.notNull(animal.getId());
        mongoTemplate.save(animal);
    }

    @Override
    public void deleteEntry(String s) {
        deleteEntry(findEntry(s));
    }

    @Override
    public void deleteEntry(MongoDbAnimal o) {
        Assert.notNull(o.getId());
        mongoTemplate.remove(o);
    }

    @Override
    public int countAll() {
        MapReduceResults<DBObject> results = mongoTemplate.mapReduce("animal", "classpath:scripts/map_all.js", "classpath:scripts/reduce_count.js", DBObject.class);
        Iterator<DBObject> valueObjectIterator = results.iterator();
        if (valueObjectIterator.hasNext()) {
            Double value = (Double) valueObjectIterator.next().get("value");
            return value.intValue();
        }
        else {
            return 0;
        }
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(new BasicQuery("{}"), MongoDbAnimal.class);
    }

    @Override
    public List<MongoDbAnimal> findBySpeciesName(String name) {
        return findAll(where("speciesName").is(name));
    }

    @Override
    public List<MongoDbAnimal> findByGenusName(String name) {
        return findAll(where("genusName").is(name));
    }

    @Override
    public List<MongoDbAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return findAll(new Criteria().andOperator(
                where("speciesName").is(speciesName),
                where("genusName").is(genusName)));
    }

    @Override
    public List<MongoDbAnimal> findByWeight(int weight) {
        return findAll(where("weight").is(weight));
    }

    @Override
    public List<MongoDbAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return findAll(new Criteria().andOperator(
                where("weight").gte(startWeight),
                where("weight").lte(endWeight)));
    }

    @Override
    public List<MongoDbAnimal> findByWeightOrLength(int size) {
        return findAll(new Criteria().orOperator(
                where("weight").is(size),
                where("length").is(size)));
    }

    @Override
    public List<MongoDbAnimal> findByArea(String area) {
        return findAll(where("areas").in(area));
    }

    @Override
    public List<MongoDbAnimal> findByAreaIn(String... area) {
        return findAll(where("areas").in(area));
    }

    private MongoDbAnimal find(CriteriaDefinition criteriaDefinition) {
        List<MongoDbAnimal> all = findAll(criteriaDefinition);
        switch (all.size()) {
            case 0: return null;
            case 1: return all.get(0);
            default: throw new IllegalStateException();
        }
    }

    private List<MongoDbAnimal> findAll(CriteriaDefinition criteriaDefinition) {
        return mongoTemplate.find(query(criteriaDefinition), MongoDbAnimal.class);
    }
}
