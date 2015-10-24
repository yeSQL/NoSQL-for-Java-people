    package com.github.yesql.couchdb.test;

import com.github.yesql.couchdb.dao.AnimalDao;
import com.github.yesql.couchdb.model.Animal;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = {AnimalDaoIntegrationTest.DefaultConfig.class}
)
public abstract class AnimalDaoIntegrationTest extends AnimalDaoTestBase {

    protected AnimalDaoIntegrationTest(Class<? extends Animal> modelClass) {
        super(modelClass);
    }

    @Configuration
    public static class DefaultConfig {
    }

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    protected AnimalDao dao;

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test(groups = "create")
    public void testSave() {
        for (Animal animal : generate10Animals()) {
            logger.info("Saving " + animal);
            dao.saveEntry(animal);
            assertNotNull(animal.getId());
        }
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindAll() {
        List<Animal> animals  = dao.findAllEntries();

        assertEquals(animals.size(), 10);
    }

    @Test(groups = "read", dependsOnGroups = "create", dependsOnMethods = "testFindAll")
    public void testFind() {
        List<Animal> allEntries = dao.findAllEntries();
        for (Animal a1 : allEntries) {
            Animal a2 = (Animal) dao.findEntry(a1.getId());
            assertEquals(compareAnimal(a1, a2), 0);
        }
    }

    @Test(groups = "read", dependsOnGroups = "create", dependsOnMethods = "testFindAll")
    public void testUpdate() {
        Animal a = first();
        int length = a.getLength();
        a.setLength(101);
        dao.updateEntry(a);

        assertEquals(a.getLength(), 101);
        Animal animal = (Animal) dao.findEntry(a.getId());
        assertEquals(animal.getLength(), 101);

        // cleanup

        a.setLength(length);
        dao.updateEntry(a);

        assertEquals(a.getLength(), length);
        animal = (Animal) dao.findEntry(a.getId());
        assertEquals(animal.getLength(), length);
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindBySpeciesName() throws Exception {
        for (String speciesName : SPECIES_NAMES) {
            Animal animal = (Animal) one(dao.findBySpeciesName(speciesName));
            assertEquals(animal.getSpeciesName(), speciesName);
        }
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByGenusName() throws Exception {
        for (String genusName : GENUS_NAMES) {
            Animal animal = (Animal) one(dao.findByGenusName(genusName));
            assertEquals(animal.getGenusName(), genusName);
        }
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void  testFindBySpeciesNameAndGenusName() throws  Exception {
        for (int i = 0; i < SPECIES_NAMES.length; i++) {
            String speciesName = SPECIES_NAMES[i];
            String genusName = GENUS_NAMES[i];

            Animal animal = (Animal) one(dao.findBySpeciesNameAndGenusName(speciesName, genusName));
            assertEquals(animal.getGenusName(), genusName);
            assertEquals(animal.getSpeciesName(), speciesName);
        }

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByWeight() throws Exception {
        for (int weight : WEIGHTS) {
            Animal animal = (Animal) one(dao.findByWeight(weight));
            assertEquals(animal.getWeight(), weight);
        }

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByWeightBetween() throws Exception {
        List<Animal> animals = dao.findByWeightBetween(3, 7);

        assertEquals(animals.size(), 5);
        for (Animal animal : animals) {
            assertEquals(compareAnimal(animal, buildAnimalResult(animal.getSpeciesName())), 0);
        }
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByWeightOrLength() throws Exception {
        List<Animal> animals = dao.findByWeightOrLength(4);

        assertEquals(animals.size(), 2);
        for (Animal animal : animals) {
            assertEquals(compareAnimal(animal, buildAnimalResult(animal.getSpeciesName())), 0);
        }

        animals = dao.findByWeightOrLength(5);
        assertEquals(animals.size(), 1);
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByArea() throws Exception {
        for (List<String> areas : AREAS) {
            for (String area : areas) {
                Animal animal = (Animal) one(dao.findByArea(area));
                assertTrue(areas.containsAll(animal.getAreas()));
            }
        }
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByAreaIn() throws Exception {
        for (List<String> areas : AREAS) {
            Animal animal = (Animal) one(dao.findByAreaIn(areas.toArray(new String[areas.size()])));
            assertEquals(animal.getAreas(), areas);
        }
        for (List<String> areas : AREAS) {
            Animal animal = (Animal) one(dao.findByAreaIn(first(areas)));
            assertEquals(animal.getAreas(), areas);
        }

        Animal animal = (Animal) one(dao.findByAreaIn());
        assertNull(animal);

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testCount() throws Exception {
        List<Animal> allEntries = dao.findAllEntries();
        assertEquals(allEntries.size(), dao.countAll());
    }

    @Test(dependsOnGroups = "read", alwaysRun = true)
    public void testDeleteAll() {
        List<Animal> allEntries = dao.findAllEntries();

        dao.deleteEntry(allEntries.get(0));

        assertEquals(dao.countAll(), dao.findAllEntries().size());

        dao.deleteAll();

        assertEquals(dao.countAll(), 0);
    }

    @AfterSuite
    public void cleanup() {
        dao.deleteAll();
    }

    protected Animal first() {
        List<Animal> animals = dao.findAllEntries();
        return first(animals);
    }

    protected <T> T first(List<T> list) {
        return !CollectionUtils.isEmpty(list) ? list.get(0) : null;
    }

    protected <T> T one(List<T> list) {
        switch (list.size()) {
            case 0: return null;
            case 1: return list.get(0);
            default: throw new IllegalStateException();
        }
    }

}