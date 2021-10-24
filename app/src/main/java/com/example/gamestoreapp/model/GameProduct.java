package com.example.gamestoreapp.model;

import android.os.Parcel;

/**
 * Implementation of Product as a digital game product.
 *
 * Contains information about what platform the product is available on,
 * as well as its sales data.
 */
public class GameProduct implements Product {

    private long id;
    private Item item;
    private int cost;
    private int amountSold;
    private int viewCount;
    private boolean isMobile = false;

    /**
     * Constructor for a GameProduct
     */
    public GameProduct(long id, Item item, int cost, int amountSold, int viewCount){
        this.id = id;
        this.item = item;
        this.cost = cost;
        this.amountSold = amountSold;
        this.viewCount = viewCount;
    }

    /**
     * Constructor for a GameProduct including if the product is mobile or not
     */
    public GameProduct(long id, Item item, int cost, int amountSold, int viewCount, Boolean isMobile){
        this.id = id;
        this.item = item;
        this.cost = cost;
        this.amountSold = amountSold;
        this.viewCount = viewCount;
        if (isMobile != null) {
            this.isMobile = isMobile;
        }
    }

    /**
     * Constructor for a GameProduct object using a parcel
     *
     * @param parcel a parcel containing all of the information needed to create a GameProduct object
     */
    protected GameProduct(Parcel parcel) {
        id = parcel.readLong();
        item = Game.CREATOR.createFromParcel(parcel);
        cost = parcel.readInt();
        amountSold = parcel.readInt();
        viewCount = parcel.readInt();
        isMobile = parcel.readInt() > 0;
    }

    public static final Creator<GameProduct> CREATOR = new Creator<GameProduct>() {
        @Override
        public GameProduct createFromParcel(Parcel parcel) {
            return new GameProduct(parcel);
        }

        @Override
        public GameProduct[] newArray(int size) {
            return new GameProduct[size];
        }
    };

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getAmountSold() {
        return amountSold;
    }

    @Override
    public int getViewCount() {
        return viewCount;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public long getID() {
        return id;
    }

    public boolean isMobile() {return isMobile;}

    @Override
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return A formatted String representing the cost of the Product
     */
    @Override
    public String getCostAsString() {
        if (cost <= 0) {
            return "Free";
        }

        String priceString;
        int cents = cost % 100;
        int dollars = cost / 100;
        priceString = "$" + dollars;
        if (cents > 0) {
            if (cents < 10) {
                cents *= 10;
            }
            priceString += "." + cents;
        }
        return priceString;
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
        parcel.writeLong(id);
        Game game = (Game) item;
        game.writeToParcel(parcel, i);
        parcel.writeInt(cost);
        parcel.writeInt(amountSold);
        parcel.writeInt(viewCount);
        parcel.writeInt(isMobile ? 1 : 0);
    }

    /**
     * Increase the view count of this GameProduct
     */
    @Override
    public void view() {
        viewCount++;
    }

    /**
     * Increase the amount sold of this GameProduct
     */
    @Override
    public void buy() {
        amountSold++;
    }
}
