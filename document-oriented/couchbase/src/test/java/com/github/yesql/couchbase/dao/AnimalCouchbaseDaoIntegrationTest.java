package com.github.yesql.couchbase.dao;

import com.github.yesql.couchbase.CouchbaseConfig;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.model.Animal;
import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
@ContextConfiguration(
        classes = {CouchbaseConfig.class}
)
public class AnimalCouchbaseDaoIntegrationTest extends AnimalDaoIntegrationTest {

    protected AnimalCouchbaseDaoIntegrationTest() {
        super(CouchbaseAnimal.class);
    }

    @Override
    public void testFindAll() {
        List<Animal> animals  = dao.findAllEntries();

        assertEquals(animals.size(), 10);

        for (Animal animal : animals) {
            assertTrue(animal instanceof CouchbaseAnimal);
            assertNotNull(((CouchbaseAnimal) animal).getId());
            assertEquals(compareAnimal(animal, buildAnimalResult(animal.getSpeciesName())), 0);
        }
    }
}