package com.github.yesql.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Martin Janys
 */
public interface AsyncGenericDao<T, ID extends Serializable> {

    Future<T> findEntry(ID id);
    Future<List<T>> findAllEntries();
    Future<ID> saveEntry(T o);
    Future<ID> updateEntry(T o);
    Future<Boolean> deleteEntry(ID id);
    Future<Boolean> deleteEntry(T o);

    Future<Integer> countAll();
    Future<Boolean> deleteAll();
}
