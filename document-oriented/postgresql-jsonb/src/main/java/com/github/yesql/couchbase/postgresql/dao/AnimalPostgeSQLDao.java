package com.github.yesql.couchbase.postgresql.dao;

import com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal;
import com.github.yesql.couchbase.postgresql.repository.AnimalJpaRepository;
import com.github.yesql.couchdb.dao.AnimalDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalPostgeSQLDao implements AnimalDao<PostgreSQLAnimal, Long> {

    @Autowired
    private AnimalJpaRepository animalRepository;

    public PostgreSQLAnimal findEntry(Long aLong) {
        return null;
    }

    public List<PostgreSQLAnimal> findAllEntries() {
        return null;
    }

    public Long saveEntry(PostgreSQLAnimal o) {
        return null;
    }

    public void updateEntry(PostgreSQLAnimal o) {

    }

    public void deleteEntry(Long aLong) {

    }

    public void deleteEntry(PostgreSQLAnimal o) {

    }

    public int countAll() {
        return 0;
    }

    public void deleteAll() {

    }

    public List<PostgreSQLAnimal> findBySpeciesName(String name) {
        return null;
    }

    public List<PostgreSQLAnimal> findByGenusName(String name) {
        return null;
    }

    public List<PostgreSQLAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return null;
    }

    public List<PostgreSQLAnimal> findByWeight(int weight) {
        return null;
    }

    public List<PostgreSQLAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return null;
    }

    public List<PostgreSQLAnimal> findByWeightOrLength(int size) {
        return null;
    }

    public List<PostgreSQLAnimal> findByArea(String area) {
        return null;
    }

    public List<PostgreSQLAnimal> findByAreaIn(String... area) {
        return null;
    }
}
