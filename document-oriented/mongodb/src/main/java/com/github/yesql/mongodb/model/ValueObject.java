package com.github.yesql.mongodb.model;

public class ValueObject implements Comparable<ValueObject> {

    private String id;
    private int value;

    @Override
    public int compareTo(ValueObject o) {
        return Integer.valueOf(value).compareTo(o.getValue());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}