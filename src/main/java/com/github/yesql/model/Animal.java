package com.github.yesql.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Martin Janys
 */
public class Animal {

    private String speciesName;
    private String genusName;
    private int weight;
    private int length;

    private List<String> areas;

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("speciesName", speciesName)
                .add("genusName", genusName)
                .add("weight", weight)
                .add("length", length)
                .add("areas", areas)
                .toString();
    }

    public Serializable getId() {
        return null;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getGenusName() {
        return genusName;
    }

    public void setGenusName(String genusName) {
        this.genusName = genusName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }
}
