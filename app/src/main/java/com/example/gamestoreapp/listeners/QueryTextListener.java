package com.example.gamestoreapp.listeners;

import android.view.View;

import android.widget.SearchView;


public class QueryTextListener implements SearchView.OnQueryTextListener {

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
