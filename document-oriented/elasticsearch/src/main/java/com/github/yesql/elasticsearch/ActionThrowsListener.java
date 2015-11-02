package com.github.yesql.elasticsearch;

import org.elasticsearch.action.ActionListener;

/**
 * @author Martin Janys
 */
public abstract class ActionThrowsListener<T> implements ActionListener<T> {
    @Override
    public void onFailure(Throwable e) {
        throw new RuntimeException(e);
    }
}
