package com.github.yesql.couchbase.postgresql.repository;

import com.github.yesql.couchbase.postgresql.entity.PosgreSQLAnimalEntity;
import com.github.yesql.couchdb.dao.AnimalDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Martin Janys
 */
public interface AnimalJpaRepository extends CrudRepository<PosgreSQLAnimalEntity, Long> {

    @Query(value = "SELECT * FROM animal WHERE animal->>'speciesName' = :name", nativeQuery = true)
    List<PosgreSQLAnimalEntity> findBySpeciesName(@Param("name") String name);

    @Query(value = "SELECT * FROM animal WHERE animal->>'genusName' = :name", nativeQuery = true)
    List<PosgreSQLAnimalEntity> findByGenusName(@Param("name") String name);

    @Query(value = "SELECT * FROM animal WHERE animal->>'speciesName' = :speciesName and animal->>'genusName' = :genusName", nativeQuery = true)
    List<PosgreSQLAnimalEntity> findBySpeciesNameAndGenusName(@Param("speciesName") String speciesName, @Param("genusName") String genusName);

    @Query(value = "SELECT * FROM animal WHERE CAST(animal->>'weight' as INTEGER) = :weight", nativeQuery = true)
    List<PosgreSQLAnimalEntity> findByWeight(@Param("weight") int weight);

    @Query(value = "SELECT * FROM animal WHERE CAST(animal->>'weight' as INTEGER) BETWEEN :startWeight and :endWeight", nativeQuery = true)
    List<PosgreSQLAnimalEntity> findByWeightBetween(@Param("startWeight") int startWeight, @Param("endWeight") int endWeight);

    @Query(value = "SELECT * FROM animal WHERE CAST(animal->>'weight' as INTEGER) = :s OR CAST(animal->>'length' as INTEGER) = :s", nativeQuery = true)
    List<PosgreSQLAnimalEntity> findByWeightOrLength(@Param("s") int size);

    @Query(value = "SELECT * FROM animal, jsonb_array_elements_text(animal.animal->'areas') AS area WHERE area = :area", nativeQuery = true)
    List<PosgreSQLAnimalEntity> findByArea(@Param("area") String area);

    @Query(value = "SELECT * FROM animal, jsonb_array_elements_text(animal.animal->'areas') AS area WHERE area IN :areas", nativeQuery = true)
    Set<PosgreSQLAnimalEntity> findByAreaIn(@Param("areas")  String... areas);
}
