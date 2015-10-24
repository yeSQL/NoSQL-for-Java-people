package com.github.yesql.couchbase.dao;

import com.github.yesql.couchbase.CouchbaseQLConfig;
import com.github.yesql.couchbase.model.CouchbaseAnimal;
import com.github.yesql.couchdb.model.Animal;
import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

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
    @Test(dependsOnGroups = "read", alwaysRun = true, timeOut = 5000)
    public void testDeleteAll() {
        List<Animal> allEntries = dao.findAllEntries();

        dao.deleteEntry(allEntries.get(0));

        //noinspection StatementWithEmptyBody
        while (!Objects.equals(dao.countAll(), dao.findAllEntries().size()))
            ;

        //noinspection StatementWithEmptyBody
        while (!Objects.equals(dao.countAll(), 0))
            ;
    }
}