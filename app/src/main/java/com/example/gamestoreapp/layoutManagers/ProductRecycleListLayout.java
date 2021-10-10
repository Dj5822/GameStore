package com.example.gamestoreapp.layoutManagers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ProductRecycleListLayout extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public ProductRecycleListLayout(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}