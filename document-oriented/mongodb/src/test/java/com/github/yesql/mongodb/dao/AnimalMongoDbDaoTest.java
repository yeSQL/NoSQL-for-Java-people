package com.github.yesql.mongodb.dao;

import com.github.yesql.couchdb.model.Animal;
import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import com.github.yesql.mongodb.MongoDbConfig;
import com.github.yesql.mongodb.model.Address;
import com.github.yesql.mongodb.model.MongoDbAnimal;
import com.github.yesql.mongodb.model.Zoo;
import com.github.yesql.mongodb.repository.ZooRepository;
import com.google.common.collect.ComparisonChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Arrays;
import java.util.Comparator;

import static org.testng.Assert.assertNotNull;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        classes = {MongoDbConfig.class}
)
public class AnimalMongoDbDaoTest extends AnimalDaoIntegrationTest {

    @Autowired
    private ZooRepository zooRepository;
    private Zoo zoo;

    protected AnimalMongoDbDaoTest() {
        super(MongoDbAnimal.class);
    }

    @Override
    @BeforeSuite
    protected void springTestContextPrepareTestInstance() throws Exception {
        super.springTestContextPrepareTestInstance();
    }

    @BeforeSuite(dependsOnMethods = "springTestContextPrepareTestInstance")
    public void setUpSuite() {
        Zoo zoo = new Zoo();
        zoo.setName("Zoo of capital");
        zoo.setAddress(createAddress());
        zoo = zooRepository.save(zoo);
        this.zoo = zoo;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setStreet("1st");
        address.setStreetNumber("I.");
        address.setTown("Capital");
        return address;
    }

    @AfterSuite
    public void tearDownSuite() {
        zooRepository.delete(zoo);
    }

    @Override
    protected ComparisonChain createAnimalComparator(Animal a1, Animal a2) {
        MongoDbAnimal animal1 = (MongoDbAnimal) a1;
        MongoDbAnimal animal2 = (MongoDbAnimal) a2;
        return super.createAnimalComparator(a1, a2)
                .compare(animal1.getZoo(), animal2.getZoo(), (o1, o2) -> {
                    if (o1 == null && o2 == null) {
                        return 0;
                    }
                    else if (o1 == null) {
                        return 1;
                    }
                    else if (o2 == null) {
                        return -1;
                    }
                    else {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
    }

    protected Animal buildAnimalResult(String name) {
        final int index = Arrays.asList(SPECIES_NAMES).indexOf(name);
        return new MongoDbAnimal() {{
            setSpeciesName(SPECIES_NAMES[index % SPECIES_NAMES.length]);
            setGenusName(GENUS_NAMES[index % GENUS_NAMES.length]);
            setLength(LENGTHS[index % LENGTHS.length]);
            setWeight(WEIGHTS[index % WEIGHTS.length]);
            setAreas(AREAS[index % AREAS.length]);
            setZoo(zoo);
        }};
    }

    public void testSave() {
        for (Animal animal : generate10Animals()) {
            ((MongoDbAnimal)animal).setZoo(zoo);
            logger.info("Saving " + animal);
            dao.saveEntry(animal);
            assertNotNull(animal.getId());
        }
    }
}