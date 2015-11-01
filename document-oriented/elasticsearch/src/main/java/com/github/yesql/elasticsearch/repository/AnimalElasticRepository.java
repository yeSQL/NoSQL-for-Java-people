package com.github.yesql.elasticsearch.repository;

import com.github.yesql.elasticsearch.model.ElasticAnimal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Martin Janys
 */
public interface AnimalElasticRepository extends ElasticsearchRepository<ElasticAnimal, String> {
}
