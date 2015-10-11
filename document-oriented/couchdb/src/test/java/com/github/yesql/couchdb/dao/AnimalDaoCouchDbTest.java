package com.github.yesql.couchdb.dao;

import com.github.yesql.couchdb.CouchDbConfig;
import com.github.yesql.couchdb.model.Animal;
import com.github.yesql.couchdb.model.CouchDbAnimal;
import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import org.biins.objectbuilder.builder.ObjectBuilder;
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
        classes = {CouchDbConfig.class}
)
public class AnimalDaoCouchDbTest extends AnimalDaoIntegrationTest {

    public AnimalDaoCouchDbTest() {
        super(CouchDbAnimal.class);
    }

    @Override
    protected ObjectBuilder createObjectBuilder() {
        return super.createObjectBuilder()
                .onObject().ignoreProperty("revision")
                .and();
    }

    @Override
    public void testFindAll() {
        List<Animal> animals  = dao.findAllEntries();

        assertEquals(animals.size(), 10);

        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            assertTrue(animal instanceof CouchDbAnimal);
            assertNotNull(((CouchDbAnimal)animal).getId());
            assertNotNull(((CouchDbAnimal)animal).getRevision());
            assertEquals(compareAnimal(animal, buildAnimalResult(i)), 0);
        }
    }
}