package com.example.gamestoreapp.implementation;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gamestoreapp.interfaces.Item;

import java.util.List;

public class Game implements Item {

    private long id;
    private String name;
    private String description;
    private List<String> imageNames;
    private String iconImageName;
    private String studioName;

    protected static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel parcel) {
            return new Game(parcel);
        }

        @Override
        public Game[] newArray(int i) {
            return new Game[0];
        }
    };

    public Game(long id, String name, String description, List<String> imageNames, String iconImageName, String studioName){
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageNames = imageNames;
        this.iconImageName = iconImageName;
        this.studioName = studioName;
    }

    public Game(Parcel parcel) {
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.description = parcel.readString();
        this.imageNames = parcel.createStringArrayList();
        this.iconImageName = parcel.readString();
        this.studioName = parcel.readString();
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
    public List<String> getImagesNames() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeStringList(imageNames);
        parcel.writeString(iconImageName);
        parcel.writeString(studioName);
    }
}
