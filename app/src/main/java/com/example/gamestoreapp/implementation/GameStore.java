package com.example.gamestoreapp.implementation;

import com.example.gamestoreapp.interfaces.Category;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.Product;
import com.example.gamestoreapp.interfaces.Store;

import java.util.ArrayList;
import java.util.List;

public class GameStore implements Store {

    @Override
    public List<Product> getBestsellingProducts() {
        List<Product> bestsellingGames = new ArrayList<>();
        for (int i=0; i<20; i++) {
            Game game = new Game(i, "test", "test", new ArrayList<String>(), "test", "test");
            bestsellingGames.add(new GameProduct(game, 0, 0, 0));
        }

        return bestsellingGames;
    }

    @Override
    public List<Product> getMostViewedProducts() {
        List<Product> bestsellingGames = new ArrayList<>();
        for (int i=0; i<20; i++) {
            Game game = new Game(i, "test", "test", new ArrayList<String>(), "test", "test");
            bestsellingGames.add(new GameProduct(game, 0, 0, 0));
        }

        return bestsellingGames;
    }

    @Override
    public List<Product> getMatchingProducts(String searchString) {
        return null;
    }
}
