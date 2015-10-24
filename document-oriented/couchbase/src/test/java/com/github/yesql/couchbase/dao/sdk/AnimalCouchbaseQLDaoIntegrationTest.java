package com.github.yesql.couchbase.dao.sdk;

import com.github.yesql.couchbase.CouchbaseQLConfig;
import com.github.yesql.couchdb.model.Animal;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
@ContextConfiguration(
        inheritLocations = false,
        classes = {CouchbaseQLConfig.class}
)
public class AnimalCouchbaseQLDaoIntegrationTest extends AnimalCouchbaseDaoIntegrationTest {


    @Test(dependsOnGroups = "read", alwaysRun = true)
    public void testDeleteAll() {
        List<Animal> allEntries = dao.findAllEntries();

        dao.deleteEntry(allEntries.get(0));
        dao.countAll();
        assertEquals(dao.countAll(), dao.findAllEntries().size());

        dao.deleteAll();
        dao.countAll();
        assertEquals(dao.countAll(), 0);
    }
}