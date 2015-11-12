package com.github.yesql.mongodb.dao;

import com.github.yesql.common.dao.AnimalDao;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import com.github.yesql.mongodb.repository.AnimalMongoDbRepository;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalMongoDbDao implements AnimalDao<MongoDbAnimal, String> {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AnimalMongoDbRepository animalRepository;

    @Override
    public MongoDbAnimal findEntry(String s) {
        return animalRepository.findOne(s);
    }

    @Override
    public List<MongoDbAnimal> findAllEntries() {
        return animalRepository.findAll();
    }

    @Override
    public String saveEntry(MongoDbAnimal o) {
        Assert.isNull(o.getId());
        MongoDbAnimal animal = animalRepository.save(o);
        return animal.getId();
    }

    @Override
    public void updateEntry(MongoDbAnimal o) {
        Assert.notNull(o.getId());
        animalRepository.save(o);
    }

    @Override
    public void deleteEntry(String s) {
        animalRepository.delete(s);
    }

    @Override
    public void deleteEntry(MongoDbAnimal o) {
        Assert.notNull(o.getId());
        deleteEntry(o.getId());
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
        animalRepository.deleteAll();
    }

    @Override
    public List<MongoDbAnimal> findBySpeciesName(String name) {
        return animalRepository.findBySpeciesName(name);
    }

    @Override
    public List<MongoDbAnimal> findByGenusName(String name) {
        return animalRepository.findByGenusName(name);
    }

    @Override
    public List<MongoDbAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return animalRepository.findBySpeciesNameAndGenusName(speciesName, genusName);
    }

    @Override
    public List<MongoDbAnimal> findByWeight(int weight) {
        return animalRepository.findByWeight(weight);
    }

    @Override
    public List<MongoDbAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return animalRepository.findByWeightBetween(startWeight, endWeight);
    }

    @Override
    public List<MongoDbAnimal> findByWeightOrLength(int size) {
        return animalRepository.findByWeightOrLength(size);
    }

    @Override
    public List<MongoDbAnimal> findByArea(String area) {
        return animalRepository.findByAreas(area);
    }

    @Override
    public List<MongoDbAnimal> findByAreaIn(String... area) {
        return animalRepository.findByAreasIn(area);
    }
}
