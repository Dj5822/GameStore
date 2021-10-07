package com.example.gamestoreapp.implementation;

import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.Product;

public class GameProduct implements Product {

    private Item item;
    private int cost;
    private int amountSold;
    private int viewCount;

    public GameProduct(Item item, int cost, int amountSold, int viewCount){
        this.item = item;
        this.cost = cost;
        this.amountSold = amountSold;
        this.viewCount = viewCount;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getAmountSold() {
        return amountSold;
    }

    @Override
    public int getViewCount() {
        return viewCount;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void setViewCount(int viewCount) {
        //todo: need to update database here with new view count
        this.viewCount = viewCount;
    }
}
