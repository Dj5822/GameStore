package com.example.gamestoreapp.model;

import android.os.Parcelable;

/**
 * Implementation of a sellable digital product. Contains sales data for a product as well as
 * a reference to the underlying item.
 *
 * Is parcelable so that it can be efficiently passed between activities.
 */
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
