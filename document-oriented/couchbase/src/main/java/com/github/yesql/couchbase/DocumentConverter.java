package com.github.yesql.couchbase;

import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.view.ViewRow;
import com.github.yesql.couchdb.Identifiable;
import com.google.gson.Gson;
import rx.Observable;

import java.util.*;

/**
 * @author Martin Janys
 */
public class DocumentConverter {

    private static final Gson gson = new Gson();

    public static <T> T convert(RawJsonDocument document, Class<T> targetType) {
        return gson.fromJson(document.content(), targetType);
    }

    public static <T> T convert(ViewRow viewRow, Class<T> targetType) {
        RawJsonDocument document = viewRow.document(RawJsonDocument.class);
        return convert(document, targetType);
    }

    public static <T> List<T> convert(Collection<ViewRow> viewRows, Class<T> targetType) {
        Set<T> result = new HashSet<>(viewRows.size());
        for (ViewRow viewRow : viewRows) {
            result.add(convert(viewRow, targetType));
        }
        return new ArrayList<>(result);
    }

    public static RawJsonDocument convert(Identifiable<String> obj) {
        String json = gson.toJson(obj);
        return RawJsonDocument.create(obj.getId(), json);
    }

    public static List<RawJsonDocument> convert(Collection<Identifiable<String>> objs) {
        List<RawJsonDocument> result = new ArrayList<RawJsonDocument>(objs.size());
        for (Identifiable<String> obj : objs) {
            result.add(convert(obj));
        }
        return result;
    }

    public static <T> T convert(String s, Class<T> cls) {
        return gson.fromJson(s, cls);
    }
}
