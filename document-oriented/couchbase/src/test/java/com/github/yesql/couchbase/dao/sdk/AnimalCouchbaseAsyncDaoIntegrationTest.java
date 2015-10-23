package com.github.yesql.couchbase.dao.sdk;

import com.github.yesql.couchbase.sdk.AsyncCouchbaseConfig;
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