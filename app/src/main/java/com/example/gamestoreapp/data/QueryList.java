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
    int size = 0;

    public QueryList(QueryHandler.QueryListener queryListener) {
        this.queryListener = queryListener;
    }

    public void setExpectedQuerySize(int expectedQuerySize) {
        this.expectedQuerySize = expectedQuerySize;
    }

    public void addWrapper(int pos,Object object) {
        for (int i = size(); i <= pos; i++) {
            super.add(i, null);
        }
        super.set(pos, object);
        size++;
        if (sizeWrapper() == expectedQuerySize) {
            queryListener.OnQueryComplete();
        }
    }

    public int sizeWrapper() {
        return size;
    }
}
