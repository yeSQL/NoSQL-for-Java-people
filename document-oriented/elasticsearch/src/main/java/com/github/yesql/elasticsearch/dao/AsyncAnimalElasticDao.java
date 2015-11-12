package com.github.yesql.elasticsearch.dao;

import com.github.yesql.common.dao.AsyncAnimalDao;
import com.github.yesql.elasticsearch.model.ElasticAnimal;

/**
 * @author Martin Janys
 */
public interface AsyncAnimalElasticDao extends AsyncAnimalDao<ElasticAnimal, String> {

}
