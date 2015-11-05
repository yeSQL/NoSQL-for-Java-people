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
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.util.RetryAnalyzerCount;

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

    private static final class Retry extends RetryAnalyzerCount {
        @Override
        public boolean retryMethod(ITestResult result) {
            return !result.isSuccess();
        }
    }

    public AnimalElasticSpringDaoTest() {
        super(ElasticAnimal.class);
    }

    @BeforeMethod
    public void refresh() {
        client.indices().prepareFlush("animal").execute();
        client.indices().prepareClearCache("animal").execute();
        client.indices().prepareRefresh("animal").execute();
    }

    @Test(groups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testSave() {
        super.testSave();
    }

    @Test(dependsOnGroups = "read", alwaysRun = true, retryAnalyzer = Retry.class)
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

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindAll() {
        super.testFindAll();
    }

    @Test(groups = "read", dependsOnGroups = "create", dependsOnMethods = "testFindAll", retryAnalyzer = Retry.class)
    @Override
    public void testFind() {
        super.testFind();
    }

    @Test(groups = "read", dependsOnGroups = "create", dependsOnMethods = "testFindAll", retryAnalyzer = Retry.class)
    @Override
    public void testUpdate() {
        super.testUpdate();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindBySpeciesName() throws Exception {
        super.testFindBySpeciesName();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindByGenusName() throws Exception {
        super.testFindByGenusName();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindBySpeciesNameAndGenusName() throws Exception {
        super.testFindBySpeciesNameAndGenusName();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindByWeight() throws Exception {
        super.testFindByWeight();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindByWeightBetween() throws Exception {
        super.testFindByWeightBetween();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindByWeightOrLength() throws Exception {
        super.testFindByWeightOrLength();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindByArea() throws Exception {
        super.testFindByArea();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testFindByAreaIn() throws Exception {
        super.testFindByAreaIn();
    }

    @Test(groups = "read", dependsOnGroups = "create", retryAnalyzer = Retry.class)
    @Override
    public void testCount() throws Exception {
        super.testCount();
    }
}