package com.github.yesql.dao;

import com.github.yesql.test.AnimalDaoIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Martin Janys
 */
@ContextConfiguration(
        classes = {AnimalDaoCouchDbTest.CouchDbConfig.class}
)
public class AnimalDaoCouchDbTest extends AnimalDaoIntegrationTest {

    @Configuration
    public static class CouchDbConfig {
        @Bean
        public AnimalDao animalDao() {
            return new AnimalCouchDbDao();
        }
    }
}