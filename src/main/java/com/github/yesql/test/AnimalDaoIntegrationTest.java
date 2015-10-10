package com.github.yesql.test;

import com.github.yesql.dao.AnimalDao;
import com.github.yesql.model.Animal;
import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.builder.generator.ValuesGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = {AnimalDaoIntegrationTest.DefaultConfig.class}
)
public abstract class AnimalDaoIntegrationTest extends AbstractTestNGSpringContextTests {

    protected static final String[] SPECIES_NAMES = new String[]{
            "ant", "bat", "cobra", "donkey", "eagle", "fox", "gorilla", "hyena", "jaguar", "kangaroo"
    };
    protected static final String[] GENUS_NAMES = new String[]{
            "pharaoh", "big", "indian", "domestic", "sea", "vulpes", "mountain", "black-headed", "panther", "australian"
    };
    protected static final Integer[] WEIGHTS = new Integer[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    };
    protected static final Integer[] LENGTHS = new Integer[]{
            10, 9, 9, 7, 6, 5, 4, 3, 2, 1
    };
    protected static final List[] AREAS = new List[]{
            Arrays.asList("forrest"),
            Arrays.asList("cave"),
            Arrays.asList("desert", "India"),
            Arrays.asList("America", "steppe"),
            Arrays.asList("Europe", "Asia"),
            Arrays.asList("forrest", "Europe", "America"),
            Arrays.asList("Africa", "rain forrest"),
            Arrays.asList("Africa", "Asia"),
            Arrays.asList("South America"),
            Arrays.asList("Australia")
    };


    @Configuration
    public static class DefaultConfig {
    }

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private AnimalDao dao;

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    protected List<Animal> generate10Animals() {
        return generateAnimals(10);
    }

    private List<Animal> generateAnimals(int count) {
        return new ObjectBuilder()
                .onObject()
                    .ignoreProperty("id")
                    // String speciesName;
                    .onProperty("speciesName", new ValuesGenerator<String>(SPECIES_NAMES))
                    // String familyName;
                    .onProperty("genusName", new ValuesGenerator<String>(GENUS_NAMES))
                    // int weight;
                    .onProperty("weight", new ValuesGenerator<Integer>(WEIGHTS))
                    // int length;
                    .onProperty("length", new ValuesGenerator<Integer>(LENGTHS))
                    // List<String> areas;
                    .onProperty("areas", new ValuesGenerator<List>(AREAS))
                .build(Animal.class, count);
    }

    @Test(groups = "create")
    public void testSave() {
        List<Animal> animals = generateAnimals(10);
    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFind() {

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testUpdate() {

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindBySpeciesName() throws Exception {

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByGenusName() throws Exception {

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByWeight() throws Exception {

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByWeightBetween() throws Exception {

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByArea() throws Exception {

    }

    @Test(groups = "read", dependsOnGroups = "create")
    public void testFindByAreaIn() throws Exception {

    }

    @Test(dependsOnGroups = "read")
    public void testDelete() {

    }
}