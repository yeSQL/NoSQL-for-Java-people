package com.github.yesql.mongodb.repository;

import com.github.yesql.mongodb.model.Zoo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Martin Janys
 */
@Repository
public interface ZooRepository extends MongoRepository<Zoo, String> {
}
