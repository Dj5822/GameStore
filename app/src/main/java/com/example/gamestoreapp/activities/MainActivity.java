package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.MainItemAdaptor;
import com.example.gamestoreapp.adaptors.ProductAdaptor;
import com.example.gamestoreapp.implementation.Game;
import com.example.gamestoreapp.implementation.GameProduct;
import com.example.gamestoreapp.implementation.GameStore;
import com.example.gamestoreapp.interfaces.ListActivity;
import com.example.gamestoreapp.interfaces.Product;
import com.example.gamestoreapp.interfaces.Store;
import com.example.gamestoreapp.listeners.CategoryClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Store store;
    private ViewHolder vh;

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

    void startSearch(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vh = new ViewHolder();

        vh.actionCardView.setOnClickListener(new CategoryClickListener(ActionListActivity.class));
        vh.strategyCardView.setOnClickListener(new CategoryClickListener(StrategyListActivity.class));
        vh.casualCardView.setOnClickListener(new CategoryClickListener(CasualListActivity.class));
        vh.simulationCardView.setOnClickListener(new CategoryClickListener(SimulationListActivity.class));
        vh.bestsellingButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_background));

        vh.bestsellingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vh.bestsellingButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_background));
                vh.mostViewedButton.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        vh.mostViewedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vh.bestsellingButton.setBackgroundColor(Color.TRANSPARENT);
                vh.mostViewedButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_background));
            }
        });

        vh.productListView.setLayoutManager(new LinearLayoutManager(this));

        propagateAdapter();
    }

    private void fetchData() {


    }

    private void propagateAdapter() {
        List<String> productList = new ArrayList<>();
        productList.add("test");
        productList.add("test2");
        productList.add("test3");
        productList.add("test");
        productList.add("test2");
        productList.add("test3");
        productList.add("test");
        productList.add("test2");
        productList.add("test3");
        productList.add("test");
        productList.add("test2");
        productList.add("test3");


        MainItemAdaptor adapter = new MainItemAdaptor(this, productList);
        vh.productListView.setAdapter(adapter);

        vh.mainProgressBar.setVisibility(View.GONE);
    }
}