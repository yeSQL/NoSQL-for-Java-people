package com.github.yesql.common.dao;

import com.github.yesql.common.model.Animal;
import rx.Observable;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Martin Janys
 */
public class ObservableToFutureWrapperDao<ANIMAL extends Animal, ID extends Serializable> implements AsyncAnimalDao<ANIMAL, ID> {

    private final ObservableAnimalDao<ANIMAL, ID> observableAnimalDao;
    private final long timeoutInSec;

    public ObservableToFutureWrapperDao(ObservableAnimalDao<ANIMAL, ID> observableAnimalDao, long timeoutInSec) {
        this.observableAnimalDao = observableAnimalDao;
        this.timeoutInSec = timeoutInSec;
    }

    private <T> Future<T> toFuture(Observable<T> observable) {
        return observable
                .timeout(timeoutInSec, TimeUnit.SECONDS)
                .toBlocking()
                .toFuture();
    }

    public Future<ANIMAL> findEntry(ID id) {
        return toFuture(observableAnimalDao.findEntry(id));
    }

    public Future<List<ANIMAL>> findAllEntries() {
        return toFuture(observableAnimalDao.findAllEntries().toList());
    }

    public Future<ID> saveEntry(ANIMAL animal) {
        return toFuture(observableAnimalDao.saveEntry(animal));
    }

    public Future<ID> updateEntry(ANIMAL animal) {
        return toFuture(observableAnimalDao.updateEntry(animal));
    }

    public Future<Boolean> deleteEntry(ID id) {
        return toFuture(observableAnimalDao.deleteEntry(id));
    }

    public Future<Boolean> deleteEntry(ANIMAL animal) {
        return toFuture(observableAnimalDao.deleteEntry(animal));
    }

    public Future<Integer> countAll() {
        return toFuture(observableAnimalDao.countAll());
    }

    public Future<Boolean> deleteAll() {
        return toFuture(observableAnimalDao.deleteAll());
    }

    public Future<List<ANIMAL>> findBySpeciesName(String name) {
        return toFuture(observableAnimalDao.findBySpeciesName(name).toList());
    }

    public Future<List<ANIMAL>> findByGenusName(String name) {
        return toFuture(observableAnimalDao.findByGenusName(name).toList());
    }

    public Future<List<ANIMAL>> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return toFuture(observableAnimalDao.findBySpeciesNameAndGenusName(speciesName, genusName).toList());
    }

    public Future<List<ANIMAL>> findByWeight(int weight) {
        return toFuture(observableAnimalDao.findByWeight(weight).toList());
    }

    public Future<List<ANIMAL>> findByWeightBetween(int startWeight, int endWeight) {
        return toFuture(observableAnimalDao.findByWeightBetween(startWeight, endWeight).toList());
    }

    public Future<List<ANIMAL>> findByWeightOrLength(int size) {
        return toFuture(observableAnimalDao.findByWeightOrLength(size).toList());
    }

    public Future<List<ANIMAL>> findByArea(String area) {
        return toFuture(observableAnimalDao.findByArea(area).toList());
    }

    public Future<List<ANIMAL>> findByAreaIn(String... area) {
        return toFuture(observableAnimalDao.findByAreaIn(area).toList());
    }
}
