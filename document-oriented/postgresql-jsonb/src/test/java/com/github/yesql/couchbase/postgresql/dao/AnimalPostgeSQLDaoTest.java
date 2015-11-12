package com.github.yesql.couchbase.postgresql.dao;

import com.github.yesql.couchbase.postgresql.PostgreSQLConfig;
import com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal;
import com.github.yesql.common.test.AnimalDaoIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        classes = {PostgreSQLConfig.class}
)
public class AnimalPostgeSQLDaoTest extends AnimalDaoIntegrationTest {

    public AnimalPostgeSQLDaoTest() {
        super(PostgreSQLAnimal.class);
    }
}