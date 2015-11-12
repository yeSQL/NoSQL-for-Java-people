package com.github.yesql.common.dao;

import com.github.yesql.common.model.Animal;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Martin Janys
 */
public class FutureToSyncWrapperDao<ANIMAL extends Animal, ID extends Serializable> implements AnimalDao<ANIMAL, ID> {

    private final AsyncAnimalDao<ANIMAL, ID> asyncAnimalDao;
    private final long TIMEOUT = 8;

    public FutureToSyncWrapperDao(AsyncAnimalDao<ANIMAL, ID> asyncAnimalDao) {
        this.asyncAnimalDao = asyncAnimalDao;
    }


    public ANIMAL findEntry(ID id) {
        try {
            return asyncAnimalDao.findEntry(id).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findAllEntries() {
        try {
            return asyncAnimalDao.findAllEntries().get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public ID saveEntry(ANIMAL animal) {
        try {
            return (ID) asyncAnimalDao.saveEntry(animal).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEntry(ANIMAL animal) {
        try {
            asyncAnimalDao.updateEntry(animal).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEntry(ID id) {
        try {
            asyncAnimalDao.deleteEntry(id).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEntry(ANIMAL animal) {
        try {
            asyncAnimalDao.deleteEntry(animal).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public int countAll() {
        try {
            return asyncAnimalDao.countAll().get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        try {
            asyncAnimalDao.deleteAll().get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findBySpeciesName(String name) {
        try {
            return asyncAnimalDao.findBySpeciesName(name).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByGenusName(String name) {
        try {
            return asyncAnimalDao.findByGenusName(name).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        try {
            return asyncAnimalDao.findBySpeciesNameAndGenusName(speciesName, genusName).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByWeight(int weight) {
        try {
            return asyncAnimalDao.findByWeight(weight).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByWeightBetween(int startWeight, int endWeight) {
        try {
            return asyncAnimalDao.findByWeightBetween(startWeight, endWeight).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByWeightOrLength(int size) {
        try {
            return asyncAnimalDao.findByWeightOrLength(size).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByArea(String area) {
        try {
            return asyncAnimalDao.findByArea(area).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ANIMAL> findByAreaIn(String... area) {
        try {
            return asyncAnimalDao.findByAreaIn(area).get(TIMEOUT, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
