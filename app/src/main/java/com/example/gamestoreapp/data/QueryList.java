package com.example.gamestoreapp.data;

import com.example.gamestoreapp.interfaces.Product;

import java.util.ArrayList;

/**
 * An Arraylist for use with queries.
 * Has support for the QueryListener to detect when the query is complete.
 */
public class QueryList<Object> extends ArrayList<Object> {

    QueryHandler.QueryListener queryListener; // Listener for query completion
    int expectedQuerySize = 0; // the number of products we expect to get from the query
    int size = 0; // the number of products actually received from the query

    public QueryList(QueryHandler.QueryListener queryListener) {
        this.queryListener = queryListener;
    }

    public void setExpectedQuerySize(int expectedQuerySize) {
        this.expectedQuerySize = expectedQuerySize;
    }

    /**
     * Add products to the list while updating size and monitoring completion status
     * @param pos position to add at
     * @param object object to add
     */
    public void addWrapper(int pos,Object object) {
        for (int i = size(); i <= pos; i++) {
            super.add(i, null);
        }
        super.set(pos, object);
        size++;
        if (sizeWrapper() == expectedQuerySize) {
            complete();
        }
    }

    public int sizeWrapper() {
        return size;
    }

    public void complete() {
        queryListener.OnQueryComplete();
    }
}
