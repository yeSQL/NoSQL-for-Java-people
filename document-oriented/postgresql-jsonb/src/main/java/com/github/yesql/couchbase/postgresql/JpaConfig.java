package com.github.yesql.couchbase.postgresql;

import com.github.yesql.common.PropertyPlaceHolderConfiguration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Martin Janys
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/postgresql.properties")
@Import({PropertyPlaceHolderConfiguration.class})
public class JpaConfig {

    @Value("${postgresql.dialect}") String dialect;
    @Value("${postgresql.ddl.auto}") String ddlAuto;
    @Value("${postgresql.show.sql}") String showSql;
    @Value("${postgresql.format.sql}") String formatSql;
    @Value("${postgresql.batch.size}") int batchSize;

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
        jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());
        return jpaTransactionManager;
    }

    @Bean
    public PersistenceProvider hibernatePersistenceProvider() {
        return new HibernatePersistenceProvider();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, PersistenceProvider persistenceProvider) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceProvider(persistenceProvider);
        entityManagerFactoryBean.setJpaProperties(propertiesFactoryBean());
        entityManagerFactoryBean.setPackagesToScan("com.github.yesql.couchbase.postgresql.entity");
        return entityManagerFactoryBean;
    }

    public Properties propertiesFactoryBean() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", dialect);
        props.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        props.setProperty("hibernate.show_sql", showSql);
        props.setProperty("hibernate.format_sql", formatSql);
        props.setProperty("hibernate.jdbc.batch_size", String.valueOf(batchSize));;
        return props;
    }

}
