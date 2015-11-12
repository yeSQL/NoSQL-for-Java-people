package com.github.yesql.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.github.yesql.common.dao.AnimalDao;
import com.github.yesql.dynamodb.model.DynamoDbAnimal;

import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalDynamoDbDao extends AbstractDynamoDbDao<DynamoDbAnimal, String, Object> implements AnimalDao<DynamoDbAnimal, String> {

    public AnimalDynamoDbDao(AmazonDynamoDB client) {
        super(DynamoDbAnimal.class, client);
    }

    @Override
    public DynamoDbAnimal findEntry(String s) {
        return findOne(s);
    }

    @Override
    public List<DynamoDbAnimal> findAllEntries() {
        return findAll();
    }

    @Override
    public String saveEntry(DynamoDbAnimal dynamoDbAnimal) {
        return save(dynamoDbAnimal);
    }

    @Override
    public void updateEntry(DynamoDbAnimal dynamoDbAnimal) {
        update(dynamoDbAnimal);
    }

    @Override
    public void deleteEntry(String s) {
        delete(s);
    }

    @Override
    public void deleteEntry(DynamoDbAnimal dynamoDbAnimal) {
        delete(dynamoDbAnimal);
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
