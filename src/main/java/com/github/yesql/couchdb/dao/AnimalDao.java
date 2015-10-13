package com.github.yesql.couchdb.dao;

import com.github.yesql.couchdb.model.Animal;

import java.io.Serializable;
import java.util.List;

/**
 * @author Martin Janys
 */
public interface AnimalDao<ANIMAL extends Animal, ID extends Serializable> extends GenericDao<ANIMAL, ID> {

    List<ANIMAL> findBySpeciesName(String name);
    List<ANIMAL> findByGenusName(String name);
    List<ANIMAL> findBySpeciesNameAndGenusName(String speciesName, String genusName);
    List<ANIMAL> findByWeight(int weight);
    List<ANIMAL> findByWeightBetween(int startWeight, int endWeight);
    List<ANIMAL> findByWeightOrLength(int size);
    List<ANIMAL> findByArea(String area);
    List<ANIMAL> findByAreaIn(String ... area);

}
