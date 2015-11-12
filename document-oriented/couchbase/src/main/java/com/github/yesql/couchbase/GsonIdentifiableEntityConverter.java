package com.github.yesql.couchbase;

import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.repository.mapping.DefaultEntityConverter;
import com.github.yesql.common.Identifiable;
import com.google.gson.Gson;

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
