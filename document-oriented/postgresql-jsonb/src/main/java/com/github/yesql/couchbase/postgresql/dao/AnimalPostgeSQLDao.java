package com.github.yesql.couchbase.postgresql.dao;

import com.github.yesql.couchbase.postgresql.entity.PosgreSQLAnimalEntity;
import com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal;
import com.github.yesql.couchbase.postgresql.repository.AnimalJpaRepository;
import com.github.yesql.couchdb.dao.AnimalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalPostgeSQLDao implements AnimalDao<PostgreSQLAnimal, Long> {

    @Autowired
    private AnimalJpaRepository animalRepository;

    public PostgreSQLAnimal findEntry(Long id) {
        return extract(animalRepository.findOne(id));
    }

    private PostgreSQLAnimal extract(PosgreSQLAnimalEntity entity) {
        Assert.notNull(entity);
        return entity.getAnimal();
    }

    private List<PostgreSQLAnimal> extract(Iterable<PosgreSQLAnimalEntity> entities) {
        Assert.notNull(entities);
        List<PostgreSQLAnimal> animals = new ArrayList<>();
        for (PosgreSQLAnimalEntity entity : entities) {
            animals.add(entity.getAnimal());
        }
        return animals;
    }

    public List<PostgreSQLAnimal> findAllEntries() {
        return extract(animalRepository.findAll());
    }

    public Long saveEntry(PostgreSQLAnimal o) {
        Assert.isNull(o.getId());
        PosgreSQLAnimalEntity entity = new PosgreSQLAnimalEntity();
        entity.setAnimal(o);
        entity = animalRepository.save(entity);
        return entity.getId();
    }

    public void updateEntry(PostgreSQLAnimal o) {
        Assert.notNull(o.getId());
        PosgreSQLAnimalEntity entity = animalRepository.findOne(o.getId());
        entity.setAnimal(o);
        animalRepository.save(entity);
    }

    public void deleteEntry(Long id) {
        animalRepository.delete(id);
    }

    public void deleteEntry(PostgreSQLAnimal o) {
        Assert.notNull(o);
        deleteEntry(o.getId());
    }

    public int countAll() {
        return (int) animalRepository.count();
    }

    public void deleteAll() {
        animalRepository.deleteAll();
    }

    public List<PostgreSQLAnimal> findBySpeciesName(String name) {
        return extract(animalRepository.findBySpeciesName(name));
    }

    public List<PostgreSQLAnimal> findByGenusName(String name) {
        return extract(animalRepository.findByGenusName(name));
    }

    public List<PostgreSQLAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return extract(animalRepository.findBySpeciesNameAndGenusName(speciesName, genusName));
    }

    public List<PostgreSQLAnimal> findByWeight(int weight) {
        return extract(animalRepository.findByWeight(weight));
    }

    public List<PostgreSQLAnimal> findByWeightBetween(int startWeight, int endWeight) {
        return extract(animalRepository.findByWeightBetween(startWeight, endWeight));
    }

    public List<PostgreSQLAnimal> findByWeightOrLength(int size) {
        return extract(animalRepository.findByWeightOrLength(size));
    }

    public List<PostgreSQLAnimal> findByArea(String area) {
        return extract(animalRepository.findByArea(area));
    }

    public List<PostgreSQLAnimal> findByAreaIn(String... areas) {
        if (areas != null && areas.length > 0) {
            return extract(animalRepository.findByAreaIn(areas));
        }
        else {
            return Collections.emptyList();
        }
    }
}
