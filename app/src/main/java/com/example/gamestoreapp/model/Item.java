package com.example.gamestoreapp.model;

import android.os.Parcelable;

import java.util.List;

/**
 * Interface for a digital item. One item could be represented by
 * multiple products, with different prices and/or platforms.
 *
 * Is parcelable so that it can be efficiently passed between activities.
 */
public interface Item extends Parcelable {

    public String getName();

    public String getDescription();

    public List<String> getImagesNames();

    public String getIconImageName();

    public String getStudioName();

    public int getAgeRestriction();

}
