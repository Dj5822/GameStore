package com.example.gamestoreapp.interfaces;

import java.util.List;

public interface Store {

    public List<Product> getBestsellingProducts();

    public List<Product> getMostViewedProducts();

    public List<Product> getMatchingProducts(String searchString);

}
