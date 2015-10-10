package com.github.yesql.dao;

import com.github.yesql.model.Animal;

import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalCouchDbDao implements AnimalDao {

    public Animal find(String s) {
        return null;
    }

    public Animal save(Animal o) {
        return null;
    }

    public Animal update(Animal o) {
        return null;
    }

    public void delete(String s) {

    }

    public List<Animal> findBySpeciesName(String name) {
        return null;
    }

    public List<Animal> findByGenusName(String name) {
        return null;
    }

    public List<Animal> findByWeight(int weight) {
        return null;
    }

    public List<Animal> findByWeightBetween(int startWeight, int endWeight) {
        return null;
    }

    public List<Animal> findByArea(String area) {
        return null;
    }

    public List<Animal> findByAreaIn(String... area) {
        return null;
    }
}
