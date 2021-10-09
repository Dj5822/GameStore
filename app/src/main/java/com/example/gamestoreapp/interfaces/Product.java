package com.example.gamestoreapp.interfaces;

import android.os.Parcelable;

public interface Product extends Parcelable{

    public int getCost();

    public int getAmountSold();

    public int getViewCount();

    public Item getItem();

    public void setViewCount(int viewCount);

    public String getCostAsString();

}
