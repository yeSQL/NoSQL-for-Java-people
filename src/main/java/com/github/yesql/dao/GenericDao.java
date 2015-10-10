package com.github.yesql.dao;

/**
 * @author Martin Janys
 */
public interface GenericDao<T, ID> {

    T find(ID id);
    T save(T o);
    T update(T o);
    void delete(ID id);

}
