package com.example.gamesearch.implementation;

import com.example.gamesearch.interfaces.Category;
import com.example.gamesearch.interfaces.Product;

import java.util.List;

public class GameCategory implements Category {

    private long id;
    private String name;
    private List<Product> productList;
    private String imageName;

    public GameCategory(long id, String name, List<Product> productList, String imageName){
        this.id = id;
        this.name = name;
        this.productList = productList;
        this.imageName = imageName;
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }

}
