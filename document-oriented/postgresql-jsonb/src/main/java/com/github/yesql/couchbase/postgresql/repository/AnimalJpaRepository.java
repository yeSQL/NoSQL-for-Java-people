package com.github.yesql.couchbase.postgresql.repository;

import com.github.yesql.couchbase.postgresql.entity.PosgreSQLAnimalEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Martin Janys
 */
public interface AnimalJpaRepository extends CrudRepository<PosgreSQLAnimalEntity, Long> {

}
