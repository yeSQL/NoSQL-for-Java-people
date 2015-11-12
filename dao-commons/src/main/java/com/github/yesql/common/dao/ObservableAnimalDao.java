package com.github.yesql.common.dao;

import com.github.yesql.common.model.Animal;
import rx.Observable;

import java.io.Serializable;

/**
 * @author Martin Janys
 */
public interface ObservableAnimalDao<ANIMAL extends Animal, ID extends Serializable> extends ObservableGenericDao<ANIMAL, ID> {

    Observable<ANIMAL> findBySpeciesName(String name);
    Observable<ANIMAL> findByGenusName(String name);
    Observable<ANIMAL> findBySpeciesNameAndGenusName(String speciesName, String genusName);
    Observable<ANIMAL> findByWeight(int weight);
    Observable<ANIMAL> findByWeightBetween(int startWeight, int endWeight);
    Observable<ANIMAL> findByWeightOrLength(int size);
    Observable<ANIMAL> findByArea(String area);
    Observable<ANIMAL> findByAreaIn(String... area);

}
