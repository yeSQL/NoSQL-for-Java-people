package com.github.yesql.elasticsearch.dao;

import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.elasticsearch.model.ElasticAnimal;

import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalElasticDao implements AnimalDao<ElasticAnimal, Long> {
    @Override
    public ElasticAnimal findEntry(Long aLong) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findAllEntries() {
        return null;
    }

    @Override
    public Long saveEntry(ElasticAnimal o) {
        return null;
    }

    @Override
    public void updateEntry(ElasticAnimal o) {

    }

    @Override
    public void deleteEntry(Long aLong) {

    }

    @Override
    public void deleteEntry(ElasticAnimal o) {

    }

    @Override
    public int countAll() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ElasticAnimal> findBySpeciesName(String name) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findByGenusName(String name) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findByWeight(int weight) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findByWeightOrLength(int size) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findByArea(String area) {
        return null;
    }

    @Override
    public List<ElasticAnimal> findByAreaIn(String... area) {
        return null;
    }
}
