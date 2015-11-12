package com.github.yesql.couchbase.postgresql;

import com.github.yesql.couchbase.postgresql.dao.AnimalPostgeSQLDao;
import com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal;
import com.github.yesql.common.PropertyPlaceHolderConfiguration;
import com.github.yesql.common.dao.AnimalDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:/postgresql.properties")
@Import({
        PropertyPlaceHolderConfiguration.class,
        JpaConfig.class,
        JpaRepositoryConfig.class
})
public class PostgreSQLConfig {

    @Value("${postgresql.url}") String url;
    @Value("${postgresql.user}") String user;
    @Value("${postgresql.password}") String password;
    @Value("${postgresql.driver.class}") String driverClass;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds =  new DriverManagerDataSource(url, user, password);
        ds.setDriverClassName(driverClass);
        return ds;
    }

    @Bean
    public AnimalDao<PostgreSQLAnimal, Long> animalDao() {
        return new AnimalPostgeSQLDao();
    }
}
