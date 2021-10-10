package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.MainItemAdaptor;
import com.example.gamestoreapp.interfaces.Store;
import com.example.gamestoreapp.layout.LockableScrollView;
import com.example.gamestoreapp.layout.ProductRecycleListLayout;
import com.example.gamestoreapp.listeners.CategoryClickListener;

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
        LockableScrollView mainScrollView;

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
            mainScrollView = findViewById(R.id.main_scroll_view);
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

        ProductRecycleListLayout productRecycleListLayout = new ProductRecycleListLayout(this);
        vh.productListView.setLayoutManager(productRecycleListLayout);

        productRecycleListLayout.setScrollEnabled(false);
        vh.mainScrollView.setScrollingEnabled(true);

        vh.mainScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = vh.mainScrollView.getChildAt(vh.mainScrollView.getChildCount() - 1);
                int topDetector = vh.mainScrollView.getScrollY();
                int bottomDetector = view.getBottom() - (vh.mainScrollView.getHeight() + vh.mainScrollView.getScrollY());
                if (topDetector > 1360) {
                    productRecycleListLayout.setScrollEnabled(true);
                    vh.mainScrollView.setScrollingEnabled(false);
                }
                else {
                    productRecycleListLayout.setScrollEnabled(false);
                    vh.mainScrollView.setScrollingEnabled(true);
                }
            }
        });

        propagateAdapter();
    }

    private void fetchData() {


    }

    private void propagateAdapter() {
        List<String> productList = new ArrayList<>();
        productList.add("first element");
        productList.add("test2");
        productList.add("test3");
        productList.add("test4");
        productList.add("test6");
        productList.add("test7");
        productList.add("test8");
        productList.add("test9");
        productList.add("test10");
        productList.add("test11");
        productList.add("test12");
        productList.add("test13");
        productList.add("test14");
        productList.add("test15");
        productList.add("test16");
        productList.add("test17");
        productList.add("test18");
        productList.add("test19");
        productList.add("test20");
        productList.add("test21");
        productList.add("last element");


        MainItemAdaptor adapter = new MainItemAdaptor(this, productList);
        vh.productListView.setAdapter(adapter);

        vh.mainProgressBar.setVisibility(View.GONE);
    }
}