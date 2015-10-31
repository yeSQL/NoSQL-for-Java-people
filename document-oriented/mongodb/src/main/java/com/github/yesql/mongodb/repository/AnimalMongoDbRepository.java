package com.github.yesql.mongodb.repository;

import com.github.yesql.mongodb.model.MongoDbAnimal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Martin Janys
 */
@Repository
public interface AnimalMongoDbRepository extends MongoRepository<MongoDbAnimal, String> {
    /** Works both query or method name only */

    @Query("{ 'speciesName' : ?0 }")
    List<MongoDbAnimal> findBySpeciesName(String name);

    @Query("{ 'genusName' : ?0 }")
    List<MongoDbAnimal> findByGenusName(String name);

    @Query("{ $or: [{'speciesName': ?0}, {'genusName' : ?1}] }")
    List<MongoDbAnimal> findBySpeciesNameAndGenusName(String speciesName, String genusName);

    @Query("{ 'weight' : ?0 }")
    List<MongoDbAnimal> findByWeight(int weight);

    @Query("{ 'weight' : { $gte: ?0, $lte: ?1 } }")
    List<MongoDbAnimal> findByWeightBetween(int startWeight, int endWeight);

    /*@Query("{ $or: [{'weight': ?0}, {'length': ?0}] }")*/
    @Query("{ $where: 'this.weight==?0 || this.length==?0' }")
    List<MongoDbAnimal> findByWeightOrLength(int size);

    @Query("{ areas: {$in: [?0]} }")
    List<MongoDbAnimal> findByAreas(String area);

    @Query("{ areas: {$in: ?0} }")
    List<MongoDbAnimal> findByAreasIn(String... area);

}
