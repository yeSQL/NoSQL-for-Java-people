package com.github.yesql.couchbase.dao;

import com.github.yesql.couchbase.CouchbaseQLConfig;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.common.model.Animal;
import com.github.yesql.common.test.AnimalDaoIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
@ContextConfiguration(
        inheritLocations = false,
        classes = {CouchbaseQLConfig.class}
)
public class AnimalCouchbaseQLDaoIntegrationTest extends AnimalDaoIntegrationTest {

    protected AnimalCouchbaseQLDaoIntegrationTest() {
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

    @Override
    public void testDeleteAll() {
        List<Animal> allEntries = dao.findAllEntries();
        int count = dao.countAll();
        dao.deleteEntry(allEntries.get(0));

        dao.countAll();
        assertEquals(dao.countAll(), count - 1);

        dao.deleteAll();

        dao.countAll();
        assertEquals(dao.countAll(), 0);
    }

}