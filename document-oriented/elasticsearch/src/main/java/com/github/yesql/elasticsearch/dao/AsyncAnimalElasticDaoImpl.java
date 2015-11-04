package com.github.yesql.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yesql.elasticsearch.ActionThrowsListener;
import com.github.yesql.elasticsearch.model.ElasticAnimal;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
                future.complete(toDto(getFields.getSourceAsString()));
            }
            @Override
            public void onFailure(Throwable e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @Override
    public Future<List<ElasticAnimal>> findAllEntries() {
        CompletableFuture<List<ElasticAnimal>> future = new CompletableFuture<>();
        finAllQuery().setQuery(QueryBuilders.matchAllQuery()).execute(new ActionThrowsListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                List<ElasticAnimal> result = new ArrayList<>();
                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    ElasticAnimal elasticAnimal = toDto(hit.getSourceAsString());
                    elasticAnimal.setId(hit.getId());
                    result.add(elasticAnimal);
                }
                future.complete(result);
            }
            @Override
            public void onFailure(Throwable e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    private SearchRequestBuilder finAllQuery() {
        return client.prepareSearch(INDEX).setTypes(TYPE).setSize((int) countAllQuery().get().getCount());
    }

    @Override
    public Future<String> saveEntry(ElasticAnimal animal) {
        Assert.isNull(animal.getId());
        final CompletableFuture<String> future = new CompletableFuture<>();
        client.prepareIndex(INDEX, TYPE).setSource(toJson(animal)).execute(new ActionThrowsListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                animal.setId(indexResponse.getId());
                future.complete(indexResponse.getId());
            }

            @Override
            public void onFailure(Throwable e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @Override
    public Future<String> updateEntry(ElasticAnimal animal) {
        Assert.notNull(animal.getId());
        final CompletableFuture<String> future = new CompletableFuture<>();
        client.prepareUpdate(INDEX, TYPE, animal.getId()).setDoc(toJson(animal)).execute(new ActionThrowsListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                future.complete(updateResponse.getId());
            }
            @Override
            public void onFailure(Throwable e) {
                future.completeExceptionally(e);
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
            @Override
            public void onFailure(Throwable e) {
                future.completeExceptionally(e);
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
        countAllQuery().execute(new ActionThrowsListener<CountResponse>() {
            @Override
            public void onResponse(CountResponse countResponse) {
                future.complete((int) countResponse.getCount());
            }

            @Override
            public void onFailure(Throwable e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    private CountRequestBuilder countAllQuery() {
        return client.prepareCount(INDEX).setTypes(TYPE).setQuery(QueryBuilders.matchAllQuery());
    }

    @Override
    public Future<Boolean> deleteAll() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        finAllQuery().execute(new ActionThrowsListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                Boolean ret = Arrays.asList(searchResponse.getHits().getHits())
                        .stream()
                        .map(searchHitFields -> client.prepareDelete(INDEX, TYPE, searchHitFields.getId()).execute())
                        .map(ActionFuture::actionGet)
                        .allMatch(DeleteResponse::isFound);
                future.complete(ret);
            }

            @Override
            public void onFailure(Throwable e) {
                future.completeExceptionally(e);
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

    private ElasticAnimal toDto(String sourceAsString) {
        try {
            return mapper.readValue(sourceAsString, ElasticAnimal.class);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJson(ElasticAnimal value) {
        try {
            return mapper.writeValueAsString(value);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
