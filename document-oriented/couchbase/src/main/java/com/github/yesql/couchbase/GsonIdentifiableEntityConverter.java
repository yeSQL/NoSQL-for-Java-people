package com.github.yesql.couchbase;

import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.repository.mapping.*;
import com.github.yesql.couchdb.Identifiable;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class GsonIdentifiableEntityConverter extends DefaultEntityConverter {

    private final Gson gson = new Gson();

    @Override
    public JsonDocument fromEntity(EntityDocument<Object> source) {
        Identifiable<String> entity = (Identifiable<String>) source.content();
        return JsonDocument.create(entity.getId(), source.expiry(), JsonObject.fromJson(gson.toJson(entity)), source.cas());
    }

    @Override
    public <T> EntityDocument<T> toEntity(JsonDocument source, Class<T> clazz) {
        String json = source.content().toString();
        Identifiable<String> entity = (Identifiable<String>) gson.fromJson(json, clazz);
        return EntityDocument.create(entity.getId(), source.expiry(), (T) entity, source.cas());
    }

}
