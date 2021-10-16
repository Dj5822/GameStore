package com.example.gamestoreapp.implementation;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gamestoreapp.interfaces.Item;

import java.util.List;

public class Game implements Item {

    private String name;
    private String description;
    private List<String> imageNames;
    private String iconImageName;
    private String studioName;
    private int ageRestriction = 0;

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

    public Game(String name, String description, List<String> imageNames, String iconImageName, String studioName){
        this.name = name;
        this.description = description.replace("\\n",System.lineSeparator());
        this.imageNames = imageNames;
        this.iconImageName = iconImageName;
        this.studioName = studioName;
    }

    public Game(String name, String description, List<String> imageNames, String iconImageName, String studioName, Integer ageRestriction){
        this.name = name;
        this.description = description.replace("\\n",System.lineSeparator());
        this.imageNames = imageNames;
        this.iconImageName = iconImageName;
        this.studioName = studioName;
        if (ageRestriction != null) {
            this.ageRestriction = ageRestriction;
        }
    }

    public Game(Parcel parcel) {
        this.name = parcel.readString();
        this.description = parcel.readString();
        this.imageNames = parcel.createStringArrayList();
        this.iconImageName = parcel.readString();
        this.studioName = parcel.readString();
        this.ageRestriction = parcel.readInt();
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
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeStringList(imageNames);
        parcel.writeString(iconImageName);
        parcel.writeString(studioName);
        parcel.writeInt(ageRestriction);
    }

    @Override
    public int getAgeRestriction() {
        return ageRestriction;
    }
}
