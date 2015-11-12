package com.github.yesql.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Martin Janys
 */
public interface GenericDao<T, ID extends Serializable> {

    T findEntry(ID id);
    List<T> findAllEntries();
    ID saveEntry(T o);
    void updateEntry(T o);
    void deleteEntry(ID id);
    void deleteEntry(T o);

    int countAll();
    void deleteAll();
}
