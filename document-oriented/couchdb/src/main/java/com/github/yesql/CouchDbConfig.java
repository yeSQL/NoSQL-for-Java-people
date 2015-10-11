package com.github.yesql;

import com.github.yesql.dao.AnimalCouchDbDao;
import com.github.yesql.dao.AnimalDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:couchdb.properties")
public class CouchDbConfig extends Config {
    @Autowired
    Environment environment;
    @Value("${couchdb.name}") String dbName;
    @Value("${couchdb.host}") String host;
    @Value("${couchdb.port}") int port;
    @Value("${couchdb.maxConnections}") int maxConnections;
    @Value("${couchdb.connectionTimeout}") int connectionTimeout;
    @Value("${couchdb.socketTimeout}") int socketTimeout;
    @Value("${couchdb.username}") String username;
    @Value("${couchdb.password}") String password;
    @Bean
    public HttpClient httpClient() throws MalformedURLException {
        return new StdHttpClient.Builder()
                .host(host)
                .port(port)
                .maxConnections(maxConnections)
                .connectionTimeout(connectionTimeout)
                .socketTimeout(socketTimeout)
                .username(username)
                .password(password)
                .build();
    }
    @Bean
    public CouchDbInstance couchDbInstance(HttpClient httpClient) {
        return new StdCouchDbInstance(httpClient);
    }
    @Bean
    public CouchDbConnector couchDbConnector(CouchDbInstance couchDbInstance) {
        CouchDbConnector db = new StdCouchDbConnector(dbName, couchDbInstance);
        db.createDatabaseIfNotExists();
        return db;
    }
    @Bean
    public AnimalDao animalDao(CouchDbConnector couchDbCOnnector) {
        return new AnimalCouchDbDao(couchDbCOnnector);
    }
}
