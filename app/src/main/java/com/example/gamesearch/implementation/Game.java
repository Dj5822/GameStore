package com.example.gamesearch.implementation;

import com.example.gamesearch.interfaces.Item;

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
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<String> getDImagesNames() {
        return null;
    }

    @Override
    public String getIconImageName() {
        return null;
    }

    @Override
    public String getStudioName() {
        return null;
    }
}
