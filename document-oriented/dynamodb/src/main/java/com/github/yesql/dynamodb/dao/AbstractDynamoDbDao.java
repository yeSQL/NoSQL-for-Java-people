package com.github.yesql.dynamodb.dao;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.github.yesql.couchdb.Identifiable;

import java.util.List;

/**
 * @author Martin Janys
 */
public class AbstractDynamoDbDao<T extends Identifiable<HASH>, HASH, RANGE> {

    private final Class<T> domainClass;

    protected DynamoDBMapper dbMapper;
    protected AmazonDynamoDB client;

    public static final DynamoDBMapperConfig DEFAULT_CONFIG = new DynamoDBMapperConfig.Builder()
            .build();

    public AbstractDynamoDbDao(Class<T> domainClass, AmazonDynamoDB client) {
        this.domainClass = domainClass;
        this.client = client;
        this.dbMapper = new DynamoDBMapper(client);
    }

    public AbstractDynamoDbDao(Class<T> domainClass) {
        this.domainClass = domainClass;
        this.client = createClient();
        this.dbMapper = new DynamoDBMapper(client);
    }

    protected AmazonDynamoDB createClient() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(Regions.US_WEST_2));
        return client;
    }

    public List<String> listTables() {
        return client.listTables().getTableNames();
    }

    public T findOne(HASH hashKey) {
        return dbMapper.load(domainClass, hashKey);
    }

    public T findOne(HASH hashKey, RANGE rangeKey) {
        return dbMapper.load(domainClass, hashKey, rangeKey);
    }

    public List<T> findAll() {
        return dbMapper.scan(domainClass, new DynamoDBScanExpression());
    }

    public HASH save(T obj) {
        if (obj.getId() == null) {
            return create(obj);
        }
        else {
            update(obj);
            return obj.getId();
        }
    }

    public HASH create(T obj) {
        if (obj.getId() != null) {
            throw new IllegalArgumentException();
        }
        dbMapper.save(obj);
        return obj.getId();
    }

    public HASH update(T obj) {
        if (obj.getId() == null) {
            throw new IllegalArgumentException();
        }
        dbMapper.save(obj);
        return obj.getId();
    }

    public void delete(HASH hashKey) {
        dbMapper.delete(findOne(hashKey));
    }

    public void delete(T obj) {
        if (obj.getId() == null) {
            throw new IllegalArgumentException();
        }
        dbMapper.delete(obj);
    }

    public T queryOne(DynamoDBQueryExpression<T> query) {
        List<T> queryList = query(query);
        switch (queryList.size()) {
            case 0: return null;
            case 1: return queryList.get(0);
            default: throw new IllegalStateException();
        }
    }

    public List<T> query(DynamoDBQueryExpression<T> query) {
        return dbMapper.query(domainClass, query, DEFAULT_CONFIG);
    }

    public List<T> query(DynamoDBQueryExpression<T> query, DynamoDBMapperConfig config) {
        return dbMapper.query(domainClass, query, config);
    }

    public List<T> scan(DynamoDBScanExpression query) {
        return dbMapper.scan(domainClass, query, DEFAULT_CONFIG);
    }

    public List<T> scan(DynamoDBScanExpression query, DynamoDBMapperConfig config) {
        return dbMapper.scan(domainClass, query, config);
    }

}
