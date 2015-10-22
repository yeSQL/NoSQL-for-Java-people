package com.github.yesql.couchdb.dao;

import com.github.yesql.couchdb.model.Animal;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Martin Janys
 */
public class FutureToSyncWrapperDao<ANIMAL extends Animal, ID extends Serializable> implements AnimalDao<ANIMAL, ID> {

    private final AsyncAnimalDao<ANIMAL, ID> asyncAnimalDao;

    public FutureToSyncWrapperDao(AsyncAnimalDao<ANIMAL, ID> asyncAnimalDao) {
        this.asyncAnimalDao = asyncAnimalDao;
    }


    public ANIMAL findEntry(ID id) {
        try {
            return asyncAnimalDao.findEntry(id).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findAllEntries() {
        try {
            return asyncAnimalDao.findAllEntries().get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public ANIMAL saveEntry(ANIMAL animal) {
        try {
            return asyncAnimalDao.saveEntry(animal).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public ANIMAL updateEntry(ANIMAL animal) {
        try {
            return asyncAnimalDao.updateEntry(animal).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEntry(ID id) {
        try {
            asyncAnimalDao.deleteEntry(id).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEntry(ANIMAL animal) {
        try {
            asyncAnimalDao.deleteEntry(animal).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public int countAll() {
        try {
            return asyncAnimalDao.countAll().get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        try {
            asyncAnimalDao.deleteAll().get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findBySpeciesName(String name) {
        try {
            return asyncAnimalDao.findBySpeciesName(name).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByGenusName(String name) {
        try {
            return asyncAnimalDao.findByGenusName(name).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        try {
            return asyncAnimalDao.findBySpeciesNameAndGenusName(speciesName, genusName).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByWeight(int weight) {
        try {
            return asyncAnimalDao.findByWeight(weight).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByWeightBetween(int startWeight, int endWeight) {
        try {
            return asyncAnimalDao.findByWeightBetween(startWeight, endWeight).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByWeightOrLength(int size) {
        try {
            return asyncAnimalDao.findByWeightOrLength(size).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByArea(String area) {
        try {
            return asyncAnimalDao.findByArea(area).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByAreaIn(String... area) {
        try {
            return asyncAnimalDao.findByAreaIn(area).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
