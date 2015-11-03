package com.github.yesql.elasticsearch.dao;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.AdminClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Martin Janys
 */
public abstract class AbstractElasticDao implements InitializingBean {

    private final String[] indices;

    @Autowired
    private AdminClient adminClient;

    protected AbstractElasticDao(String ... indices) {
        this.indices = indices;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IndicesExistsResponse existsResponse = adminClient.indices().prepareExists(indices).get("10s");
        if (!existsResponse.isExists()) {
            for (String indice : indices) {
                adminClient.indices().prepareCreate(indice).get("10s");
            }
        }
    }
}
