package com.github.yesql.couchdb.dao;

import com.github.yesql.couchdb.model.Animal;
import rx.Observable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Martin Janys
 */
public interface ObservableAnimalDao<ANIMAL extends Animal, ID extends Serializable> extends ObservableGenericDao<ANIMAL, ID> {

    Observable<List<ANIMAL>> findBySpeciesName(String name);
    Observable<List<ANIMAL>> findByGenusName(String name);
    Observable<List<ANIMAL>> findBySpeciesNameAndGenusName(String speciesName, String genusName);
    Observable<List<ANIMAL>> findByWeight(int weight);
    Observable<List<ANIMAL>> findByWeightBetween(int startWeight, int endWeight);
    Observable<List<ANIMAL>> findByWeightOrLength(int size);
    Observable<List<ANIMAL>> findByArea(String area);
    Observable<List<ANIMAL>> findByAreaIn(String... area);

}
