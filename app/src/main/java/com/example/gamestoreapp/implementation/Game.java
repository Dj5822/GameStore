package com.example.gamestoreapp.implementation;

import com.example.gamestoreapp.interfaces.Item;

import java.util.List;

public class Game implements Item {

    private long id;
    private String name;
    private String description;
    private List<String> imageNames;
    private String iconImageName;
    private String studioName;

    public Game(long id, String name, String description, List<String> imageNames, String iconImageName, String studioName){
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageNames = imageNames;
        this.iconImageName = iconImageName;
        this.studioName = studioName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<String> getDImagesNames() {
        return imageNames;
    }

    @Override
    public String getIconImageName() {
        return iconImageName;
    }

    @Override
    public String getStudioName() {
        return studioName;
    }
}
