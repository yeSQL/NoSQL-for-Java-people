package com.github.yesql.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yesql.elasticsearch.ActionThrowsListener;
import com.github.yesql.elasticsearch.model.ElasticAnimal;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author Martin Janys
 */
public class AsyncAnimalElasticDaoImpl extends AbstractElasticDao implements AsyncAnimalElasticDao {

    private static final String INDEX = "animal";
    private static final String TYPE = "animal";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Client client;

    public AsyncAnimalElasticDaoImpl() {
        super(INDEX);
    }

    @Override
    public Future<ElasticAnimal> findEntry(String id) {
        final CompletableFuture<ElasticAnimal> future = new CompletableFuture<>();
        client.prepareGet(INDEX, TYPE, id).execute(new ActionThrowsListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getFields) {
                try {
                    future.complete(toDto(getFields.getSourceAsString()));
                }
                catch (IOException e) {
                    future.completeExceptionally(e);
                }
            }
        });
        return future;
    }

    @Override
    public Future<List<ElasticAnimal>> findAllEntries() {
        CompletableFuture<List<ElasticAnimal>> future = new CompletableFuture<>();
        client.prepareSearch(INDEX).setTypes(TYPE).setQuery(QueryBuilders.matchAllQuery()).execute(new ActionThrowsListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                List<ElasticAnimal> result = new ArrayList<>();
                SearchHit[] hits = searchResponse.getHits().getHits();
                try {
                    for (SearchHit hit : hits) {
                        result.add(toDto(hit.getSourceAsString()));
                    }
                    future.complete(result);
                } catch (IOException e) {
                    future.completeExceptionally(e);
                }
            }
        });
        return future;
    }

    @Override
    public Future<String> saveEntry(ElasticAnimal o) {
        final CompletableFuture<String> future = new CompletableFuture<>();
        client.prepareIndex(INDEX, TYPE).execute(new ActionThrowsListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                future.complete(indexResponse.getId());
            }
        });
        return future;
    }

    @Override
    public Future<String> updateEntry(ElasticAnimal o) {
        Assert.notNull(o.getId());
        final CompletableFuture<String> future = new CompletableFuture<>();
        client.prepareUpdate(INDEX, TYPE, o.getId()).execute(new ActionThrowsListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                future.complete(updateResponse.getId());
            }
        });
        return future;
    }

    @Override
    public Future<Boolean> deleteEntry(String id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        client.prepareDelete(INDEX, TYPE, id).execute(new ActionThrowsListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                future.complete(deleteResponse.isFound());
            }
        });
        return future;
    }

    @Override
    public Future<Boolean> deleteEntry(ElasticAnimal o) {
        Assert.notNull(o.getId());
        return deleteEntry(o.getId());
    }

    @Override
    public Future<Integer> countAll() {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        client.prepareCount(INDEX).setTypes(TYPE).execute(new ActionThrowsListener<CountResponse>() {
            @Override
            public void onResponse(CountResponse countResponse) {
                future.complete((int) countResponse.getCount());
            }
        });
        return future;
    }

    @Override
    public Future<Boolean> deleteAll() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        client.prepareSearch(INDEX).execute(new ActionThrowsListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                searchResponse.getHits().forEach(searchHitFields -> client.prepareDelete(INDEX, TYPE, searchHitFields.getId()));
                future.complete(true);
            }
        });
        return future;
    }

    @Override
    public Future<List<ElasticAnimal>> findBySpeciesName(String name) {
        return null;
    }

    @Override
    public Future<List<ElasticAnimal>> findByGenusName(String name) {
        return null;
    }

    @Override
    public Future<List<ElasticAnimal>> findBySpeciesNameAndGenusName(String speciesName, String genusName) {
        return null;
    }

    @Override
    public Future<List<ElasticAnimal>> findByWeight(int weight) {
        return null;
    }

    @Override
    public Future<List<ElasticAnimal>> findByWeightBetween(int startWeight, int endWeight) {
        return null;
    }

    @Override
    public Future<List<ElasticAnimal>> findByWeightOrLength(int size) {
        return null;
    }

    @Override
    public Future<List<ElasticAnimal>> findByArea(String area) {
        return null;
    }

    @Override
    public Future<List<ElasticAnimal>> findByAreaIn(String... area) {
        return null;
    }

    private ElasticAnimal toDto(String sourceAsString) throws IOException {
        return mapper.readValue(sourceAsString, ElasticAnimal.class);
    }
}
