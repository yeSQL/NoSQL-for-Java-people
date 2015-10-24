package com.github.yesql.couchbase.dao;

import com.github.yesql.couchbase.AsyncCouchbaseConfig;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
@ContextConfiguration(
        inheritLocations = false,
        classes = {AsyncCouchbaseConfig.class}
)
public class AnimalCouchbaseAsyncDaoIntegrationTest extends AnimalCouchbaseDaoIntegrationTest {

}