package com.example.gamesearch.implementation;

import com.example.gamesearch.interfaces.Category;
import com.example.gamesearch.interfaces.Product;
import com.example.gamesearch.interfaces.Store;

import java.util.List;

public class GameStore implements Store {

    private Long id;
    private String name;
    private List<Product> productList;
    private List<Category> categoryList;

    public GameStore(Long id, String name, List<Product> productList, List<Category> categoryList){
        this.id = id;
        this.name = name;
        this.productList = productList;
        this.categoryList = categoryList;
    }

    @Override
    public List<Product> getBestSellingProducts() {
        return null;
    }

    @Override
    public List<Product> getMostViewedProducts() {
        return null;
    }

    @Override
    public List<Product> getMatchingProducts(String searchString) {
        return null;
    }
}