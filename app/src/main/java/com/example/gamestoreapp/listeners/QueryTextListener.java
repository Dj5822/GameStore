package com.example.gamestoreapp.listeners;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.SearchView;


public class QueryTextListener implements SearchView.OnQueryTextListener {

    /**
     * Run when a query is submitted in the main view, meaning that the user it searching for a game.
     * This will search the database for relevant data and act according to this result
     *
     * @param query the query to be searched for
     * @return      will always be true as the listener will always handle the event
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        //From research it doesnt seem like firebase has a neat way of dont contains/wildcard queries
        //May instead only return only exact results or can process the entire list of games in java
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
