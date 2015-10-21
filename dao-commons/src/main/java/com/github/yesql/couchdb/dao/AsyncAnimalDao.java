package com.github.yesql.couchdb.dao;

import com.github.yesql.couchdb.model.Animal;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Martin Janys
 */
public interface AsyncAnimalDao<ANIMAL extends Animal, ID extends Serializable> extends AsyncGenericDao<ANIMAL, ID> {

    Future<List<ANIMAL>> findBySpeciesName(String name);
    Future<List<ANIMAL>> findByGenusName(String name);
    Future<List<ANIMAL>> findBySpeciesNameAndGenusName(String speciesName, String genusName);
    Future<List<ANIMAL>> findByWeight(int weight);
    Future<List<ANIMAL>> findByWeightBetween(int startWeight, int endWeight);
    Future<List<ANIMAL>> findByWeightOrLength(int size);
    Future<List<ANIMAL>> findByArea(String area);
    Future<List<ANIMAL>> findByAreaIn(String... area);

}
