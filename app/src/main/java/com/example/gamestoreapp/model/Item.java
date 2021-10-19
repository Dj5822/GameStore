package com.example.gamestoreapp.model;

import android.os.Parcelable;

import java.util.List;

public interface Item extends Parcelable {

    public String getName();

    public String getDescription();

    public List<String> getImagesNames();

    public String getIconImageName();

    public String getStudioName();

    public int getAgeRestriction();

}
