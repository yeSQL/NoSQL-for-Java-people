package com.github.yesql.common.dao;

import rx.Observable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Martin Janys
 */
public interface ObservableGenericDao<T, ID extends Serializable> {

    Observable<T> findEntry(ID id);
    Observable<T> findAllEntries();
    Observable<ID> saveEntry(T o);
    Observable<ID> updateEntry(T o);
    Observable<Boolean> deleteEntry(ID id);
    Observable<Boolean> deleteEntry(T o);

    Observable<Integer> countAll();
    Observable<Boolean>  deleteAll();
}
