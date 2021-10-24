package com.example.gamestoreapp.model;

import android.os.Parcel;

import java.util.List;

/**
 * Implementation of Item as a digital Game. Conceptually, this is a game as a work of art,
 * not as a product.
 *
 * One game could be represented by multiple products - for example, if it is available on
 * different platforms.
 */
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

    /**
     * Constructor for a game object using individual inputs of values
     */
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

    /**
     * Constructor for a game object using a parcel
     *
     * @param parcel a parcel containing all of the information needed to create a game object
     */
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

    /**
     * Writes to the provided parcel with all the values of this Game instance
     *
     * @param parcel the parcel to be written to
     */
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
