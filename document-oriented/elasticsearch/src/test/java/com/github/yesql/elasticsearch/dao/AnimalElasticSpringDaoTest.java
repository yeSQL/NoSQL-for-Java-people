package com.github.yesql.elasticsearch.dao;

import com.github.yesql.couchdb.model.Animal;
import com.github.yesql.couchdb.test.AnimalDaoIntegrationTest;
import com.github.yesql.elasticsearch.ElasticConfig;
import com.github.yesql.elasticsearch.model.ElasticAnimal;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        classes = ElasticConfig.class
)
public class AnimalElasticSpringDaoTest extends AnimalDaoIntegrationTest {

    @Autowired
    private AdminClient client;

    public AnimalElasticSpringDaoTest() {
        super(ElasticAnimal.class);
    }

    @BeforeMethod
    public void refresh() {
        client.indices().prepareFlush("animal").execute();
        client.indices().prepareClearCache("animal").execute();
        client.indices().prepareRefresh("animal").execute();
    }

    public void testDeleteAll() {
        List<Animal> allEntries = dao.findAllEntries();
        int count = dao.countAll();
        refresh();
        dao.deleteEntry(allEntries.get(0));

        refresh();
        assertEquals(dao.countAll(), count - 1);

        dao.deleteAll();

        refresh();
        assertEquals(dao.countAll(), 0);
    }
}