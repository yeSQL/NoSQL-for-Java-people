package com.github.yesql.couchbase.postgresql;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Martin Janys
 */
@Configuration
@EnableJpaRepositories("com.github.yesql.couchbase.postgresql.repository")
public class JpaRepositoryConfig {
}
