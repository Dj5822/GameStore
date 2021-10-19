package com.example.gamestoreapp.model;

import android.os.Parcel;

public class GameProduct implements Product {

    private long id;
    private Item item;
    private int cost;
    private int amountSold;
    private int viewCount;
    private boolean isMobile = false;

    public GameProduct(long id, Item item, int cost, int amountSold, int viewCount){
        this.id = id;
        this.item = item;
        this.cost = cost;
        this.amountSold = amountSold;
        this.viewCount = viewCount;
    }

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
        //todo: need to update database here with new view count
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

    @Override
    public void view() {
        viewCount++;
    }

    @Override
    public void buy() {
        amountSold++;
    }
}
