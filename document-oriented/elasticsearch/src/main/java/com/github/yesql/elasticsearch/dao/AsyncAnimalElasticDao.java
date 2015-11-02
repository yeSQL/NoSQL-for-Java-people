package com.github.yesql.elasticsearch.dao;

import com.github.yesql.couchdb.dao.AsyncAnimalDao;
import com.github.yesql.couchdb.dao.ObservableAnimalDao;
import com.github.yesql.elasticsearch.model.ElasticAnimal;

/**
 * @author Martin Janys
 */
public interface AsyncAnimalElasticDao extends AsyncAnimalDao<ElasticAnimal, String> {

}
