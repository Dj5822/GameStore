package com.example.gamestoreapp.implementation;

import com.example.gamestoreapp.interfaces.Category;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.Product;
import com.example.gamestoreapp.interfaces.Store;

import java.util.ArrayList;
import java.util.List;

public class GameStore implements Store {

    /**
     * Used to retrieve the bestselling products from the store.
     * @return a list of bestselling products.
     */
    @Override
    public List<Product> getBestsellingProducts() {
        List<Product> bestsellingGames = new ArrayList<>();
        // Should replace with a query later.
        for (int i=0; i<20; i++) {
            Game game = new Game(i, "bestselling", "test", new ArrayList<String>(), "lemon", "test");
            bestsellingGames.add(new GameProduct(game, 0, 0, 0));
        }

        return bestsellingGames;
    }

    /**
     * Used to retrieve the most viewed products from the store.
     * @return a list of the most viewed products.
     */
    @Override
    public List<Product> getMostViewedProducts() {
        List<Product> bestsellingGames = new ArrayList<>();
        // Should replace with a query once the database has been populated.
        for (int i=0; i<20; i++) {
            Game game = new Game(i, "most viewed", "test", new ArrayList<String>(), "watermelon", "test");
            bestsellingGames.add(new GameProduct(game, 10, 0, 0));
        }

        return bestsellingGames;
    }

    @Override
    public List<Product> getMatchingProducts(String searchString) {
        return null;
    }
}
