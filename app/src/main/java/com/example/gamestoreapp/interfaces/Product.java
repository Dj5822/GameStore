package com.example.gamestoreapp.interfaces;

import android.os.Parcelable;

public interface Product extends Parcelable{

    public int getCost();

    public long getID();

    public int getAmountSold();

    public int getViewCount();

    public Item getItem();

    public void setViewCount(int viewCount);

    public String getCostAsString();

    public void buy();

    public void view();

}
