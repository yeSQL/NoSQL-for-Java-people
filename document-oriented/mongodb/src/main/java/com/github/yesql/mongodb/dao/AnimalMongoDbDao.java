package com.github.yesql.mongodb.dao;

import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import com.github.yesql.mongodb.repository.AnimalMongoDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalMongoDbDao implements AnimalDao<MongoDbAnimal, String> {

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
        return (int) animalRepository.count();
    }

    @Override
    public void deleteAll() {
        animalRepository.deleteAll();
    }

    @Override
    public List<MongoDbAnimal> findBySpeciesName(String name) {
        return null;
    }

    @Override
    public List<MongoDbAnimal> findByGenusName(String name) {
        return null;
    }

    @Override
    public List<MongoDbAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return null;
    }

    @Override
    public List<MongoDbAnimal> findByWeight(int weight) {
        return null;
    }

    @Override
    public List<MongoDbAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return null;
    }

    @Override
    public List<MongoDbAnimal> findByWeightOrLength(int size) {
        return null;
    }

    @Override
    public List<MongoDbAnimal> findByArea(String area) {
        return null;
    }

    @Override
    public List<MongoDbAnimal> findByAreaIn(String... area) {
        return null;
    }
}
