package com.github.yesql.couchdb.test;

import com.github.yesql.couchdb.model.Animal;
import com.google.common.collect.ComparisonChain;
import org.apache.log4j.Logger;
import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.builder.generator.CyclicValuesGenerator;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Martin Janys
 */
public class AnimalDaoTestBase extends AbstractTestNGSpringContextTests {

    protected Logger logger = Logger.getLogger(getClass());
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
    // uniq arease
    protected static final List[] AREAS = new List[]{
            Arrays.asList("forrest ground"),
            Arrays.asList("cave"),
            Arrays.asList("desert", "India"),
            Arrays.asList("America", "steppe"),
            Arrays.asList("Europe east", "Asia"),
            Arrays.asList("forrest", "Europe", "North America"),
            Arrays.asList("Africa central", "rain forrest"),
            Arrays.asList("Africa", "Asia south"),
            Arrays.asList("South America"),
            Arrays.asList("Australia")
    };


    protected final Class<? extends Animal> modelClass;

    protected AnimalDaoTestBase(Class<? extends Animal> modelClass) {
        this.modelClass = modelClass;
    }


    protected List<Animal> generate10Animals() {
        return generateAnimals(10);
    }

    @SuppressWarnings("unchecked")
    protected List<Animal> generateAnimals(int count) {
        return (List<Animal>) createObjectBuilder()
                .build(modelClass, count);
    }

    protected ObjectBuilder createObjectBuilder() {
        return new ObjectBuilder()
                .onObject()
                .ignoreProperty("id")
                        // String speciesName;
                .onProperty("speciesName", new CyclicValuesGenerator<String>(SPECIES_NAMES))
                        // String familyName;
                .onProperty("genusName", new CyclicValuesGenerator<String>(GENUS_NAMES))
                        // int weight;
                .onProperty("weight", new CyclicValuesGenerator<Integer>(WEIGHTS))
                        // int length;
                .onProperty("length", new CyclicValuesGenerator<Integer>(LENGTHS))
                        // List<String> areas;
                .onProperty("areas", new CyclicValuesGenerator<List>(AREAS))
                .and();
    }

    @SuppressWarnings("unchecked")
    protected Animal buildAnimalResult(String name) {
        final int index = Arrays.asList(SPECIES_NAMES).indexOf(name);
        return new Animal() {{
            setSpeciesName(SPECIES_NAMES[index % SPECIES_NAMES.length]);
            setGenusName(GENUS_NAMES[index % GENUS_NAMES.length]);
            setLength(LENGTHS[index % LENGTHS.length]);
            setWeight(WEIGHTS[index % WEIGHTS.length]);
            setAreas(AREAS[index % AREAS.length]);
        }};
    }

    protected int compareAnimal(Animal a1, Animal a2) {
        return createAnimalComparator(a1, a2).result();
    }

    protected ComparisonChain createAnimalComparator(Animal a1, Animal a2) {
        return ComparisonChain.start()
                .compare(a1.getSpeciesName(), a2.getSpeciesName())
                .compare(a1.getGenusName(), a2.getGenusName())
                .compare(a1.getLength(), a2.getLength())
                .compare(a1.getWeight(), a2.getWeight())
                .compare(a1.getAreas(), a2.getAreas(), new Comparator<List<String>>() {
                    public int compare(List<String> o1, List<String> o2) {
                        int len = Math.min(o1.size(), o2.size());
                        for (int i = 0; i < len; i++) {
                            int compareTo = o1.get(0).compareTo(o2.get(0));
                            if (compareTo != 0)
                                return compareTo;
                        }
                        return 0;
                    }
                });
    }

}
