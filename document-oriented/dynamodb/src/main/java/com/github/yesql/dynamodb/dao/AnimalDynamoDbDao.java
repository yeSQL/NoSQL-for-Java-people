package com.github.yesql.dynamodb.dao;

import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.dynamodb.model.DynamoDbAnimal;

import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalDynamoDbDao extends AbstractDynamoDbDao implements AnimalDao<DynamoDbAnimal, String> {

    @Override
    public DynamoDbAnimal findEntry(String s) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findAllEntries() {
        return null;
    }

    @Override
    public DynamoDbAnimal saveEntry(DynamoDbAnimal dynamoDbAnimal) {
        return null;
    }

    @Override
    public DynamoDbAnimal updateEntry(DynamoDbAnimal dynamoDbAnimal) {
        return null;
    }

    @Override
    public void deleteEntry(String s) {

    }

    @Override
    public void deleteEntry(DynamoDbAnimal dynamoDbAnimal) {

    }

    @Override
    public int countAll() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<DynamoDbAnimal> findBySpeciesName(String s) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findByGenusName(String s) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findBySpeciesNameAndGenusName(String s, String s1) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findByWeight(int i) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findByWeightBetween(int i, int i1) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findByWeightOrLength(int i) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findByArea(String s) {
        return null;
    }

    @Override
    public List<DynamoDbAnimal> findByAreaIn(String... strings) {
        return null;
    }
}
