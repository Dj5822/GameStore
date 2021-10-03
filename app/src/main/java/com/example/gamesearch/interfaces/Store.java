package com.example.gamesearch.interfaces;

import java.util.List;

public interface Store {

    public List<Product> getBestSellingProducts();

    public List<Product> getMostViewedProducts();

    public List<Product> getMatchingProducts(String searchString);

}
