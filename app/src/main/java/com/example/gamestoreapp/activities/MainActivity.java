package com.example.gamestoreapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.MainItemAdaptor;
import com.example.gamestoreapp.data.QueryHandler;
import com.example.gamestoreapp.implementation.GameStore;
import com.example.gamestoreapp.interfaces.Product;
import com.example.gamestoreapp.interfaces.Store;
import com.example.gamestoreapp.listeners.CategoryClickListener;
import com.example.gamestoreapp.listeners.QueryTextListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Store store;
    private ViewHolder vh;
    List<Product> productList;

    /*
     * Used to get and hold view components.
     */
    private class ViewHolder {
        CardView actionCardView, strategyCardView, casualCardView, simulationCardView;
        SearchView mainSearchView;
        Button bestsellingButton, mostViewedButton;
        RecyclerView productListView;
        ProgressBar mainProgressBar;

        public ViewHolder() {
            actionCardView = findViewById(R.id.card_view_action);
            strategyCardView = findViewById(R.id.card_view_strategy);
            casualCardView = findViewById(R.id.card_view_casual);
            simulationCardView = findViewById(R.id.card_view_simulation);
            mainSearchView = findViewById(R.id.main_search_view);
            bestsellingButton = findViewById(R.id.bestselling_button);
            mostViewedButton = findViewById(R.id.most_viewed_button);
            productListView = findViewById(R.id.main_product_list_view);
            mainProgressBar = findViewById(R.id.main_progress_bar);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup view holder and store.
        vh = new ViewHolder();
        store = new GameStore();

        // Setup listeners.
        vh.actionCardView.setOnClickListener(new CategoryClickListener(ActionListActivity.class));
        vh.strategyCardView.setOnClickListener(new CategoryClickListener(StrategyListActivity.class));
        vh.casualCardView.setOnClickListener(new CategoryClickListener(CasualListActivity.class));
        vh.simulationCardView.setOnClickListener(new CategoryClickListener(SimulationListActivity.class));
        vh.bestsellingButton.setOnClickListener(view -> bestsellingProductsSelected());
        vh.mostViewedButton.setOnClickListener(view -> mostViewedProductsSelected());
        vh.mainSearchView.setOnQueryTextListener(new QueryTextListener());

        // Setup recycle view.
        LinearLayoutManager productRecycleListLayout = new LinearLayoutManager (this);
        vh.productListView.setLayoutManager(productRecycleListLayout);
        vh.productListView.setNestedScrollingEnabled(false);
        bestsellingProductsSelected();
    }

    /**
     * Highlights the bestselling button and sets
     * the recycle view contents to bestselling products.
     */
    private void bestsellingProductsSelected() {
        vh.bestsellingButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_background));
        vh.mostViewedButton.setBackgroundColor(Color.TRANSPARENT);
        productList = QueryHandler.queryField( "amountSold", new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                propagateAdapter();
            }
        });
    }

    /**
     * Highlights the most viewed button and sets
     * the recycle view contents to most viewed products.
     */
    private void mostViewedProductsSelected() {
        vh.bestsellingButton.setBackgroundColor(Color.TRANSPARENT);
        vh.mostViewedButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_background));
        productList = store.getMostViewedProducts();
        productList = QueryHandler.queryField("viewCount", new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                propagateAdapter();
            }
        });
    }

    /**
     * Used to update the recycle view.
     */
    private void propagateAdapter() {
        MainItemAdaptor adapter = new MainItemAdaptor(this, productList);
        vh.productListView.setAdapter(adapter);
        vh.mainProgressBar.setVisibility(View.GONE);
    }
}