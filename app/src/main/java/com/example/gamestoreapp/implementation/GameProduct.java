package com.example.gamestoreapp.implementation;

import android.os.Parcel;

import com.example.gamestoreapp.data.QueryHandler;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.Product;

public class GameProduct implements Product {

    private long id;
    private Item item;
    private int cost;
    private int amountSold;
    private int viewCount;

    public GameProduct(long id, Item item, int cost, int amountSold, int viewCount){
        this.id = id;
        this.item = item;
        this.cost = cost;
        this.amountSold = amountSold;
        this.viewCount = viewCount;
    }

    protected GameProduct(Parcel parcel) {
        id = parcel.readLong();
        item = Game.CREATOR.createFromParcel(parcel);
        cost = parcel.readInt();
        amountSold = parcel.readInt();
        viewCount = parcel.readInt();
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
