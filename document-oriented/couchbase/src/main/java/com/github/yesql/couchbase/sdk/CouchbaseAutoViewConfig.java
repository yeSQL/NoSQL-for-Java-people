package com.github.yesql.couchbase.sdk;

import com.couchbase.cbadmin.client.CouchbaseAdmin;
import com.couchbase.cbadmin.client.CouchbaseAdminImpl;
import com.github.yesql.couchdb.Config;
import org.biins.cauchbase.AutoViews;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Martin Janys
 */
@Configuration
@PropertySource("classpath:/couchbase.properties")
public class CouchbaseAutoViewConfig extends Config {

    @Value("${couchbase.admin.node}") String node;
    @Value("${couchbase.admin.username}") String username;
    @Value("${couchbase.admin.password}") String password;

    @Value("${couchbase.development.views}") boolean developmentViews;

    @Bean
    public CouchbaseAdmin couchbaseAdmin() throws MalformedURLException {
        return new CouchbaseAdminImpl(new URL(node), username, password);
    }

    @Bean
    public AutoViews autoViews(CouchbaseAdmin couchbaseAdmin) {
        AutoViews autoViews = new AutoViews(couchbaseAdmin);
        autoViews.setDevelopmentViews(developmentViews);
        return autoViews;
    }

}
