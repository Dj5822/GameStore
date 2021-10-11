package com.example.gamestoreapp.data;

import com.example.gamestoreapp.interfaces.Product;

import java.util.ArrayList;

/**
 * An Arraylist for use with queries.
 * Executes a method once the query is complete.
 */
public class QueryList<Object> extends ArrayList<Object> {

    QueryHandler.QueryListener queryListener;
    int expectedQuerySize = 0;

    public QueryList(QueryHandler.QueryListener queryListener) {
        this.queryListener = queryListener;
    }

    public void setExpectedQuerySize(int expectedQuerySize) {
        this.expectedQuerySize = expectedQuerySize;
    }

    @Override
    public boolean add(Object object) {
        boolean isSuccess = super.add(object);
        if (size() == expectedQuerySize) {
            queryListener.OnQueryComplete();
        }
        return isSuccess;
    }
}
