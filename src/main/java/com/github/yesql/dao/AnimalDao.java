package com.github.yesql.dao;

import com.github.yesql.model.Animal;

import java.util.List;

/**
 * @author Martin Janys
 */
public interface AnimalDao extends GenericDao<Animal, String> {

    List<Animal> findBySpeciesName(String name);
    List<Animal> findByGenusName(String name);
    List<Animal> findByWeight(int weight);
    List<Animal> findByWeightBetween(int startWeight, int endWeight);
    List<Animal> findByArea(String area);
    List<Animal> findByAreaIn(String ... area);

}
