package com.example.gamestoreapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.MainItemAdaptor;
import com.example.gamestoreapp.data.QueryHandler;
import com.example.gamestoreapp.interfaces.Product;
import com.example.gamestoreapp.listeners.CategoryClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ViewHolder vh;
    List<Product> productList;
    List<Product> searchResultList;

    /*
     * Used to get and hold view components.
     */
    private class ViewHolder {
        CardView actionCardView, strategyCardView, casualCardView, simulationCardView;
        LinearLayout searchLayout;
        SearchView mainSearchView;
        Button bestsellingButton, mostViewedButton;
        RecyclerView productListView, searchListView;
        ProgressBar mainProgressBar, searchProgressBar;
        NestedScrollView mainScrollView;

        public ViewHolder() {
            actionCardView = findViewById(R.id.card_view_action);
            strategyCardView = findViewById(R.id.card_view_strategy);
            casualCardView = findViewById(R.id.card_view_casual);
            simulationCardView = findViewById(R.id.card_view_simulation);
            mainSearchView = findViewById(R.id.main_search_view);
            bestsellingButton = findViewById(R.id.bestselling_button);
            mostViewedButton = findViewById(R.id.most_viewed_button);
            productListView = findViewById(R.id.main_product_list_view);
            searchListView = findViewById(R.id.main_product_search_list_view);
            searchLayout = findViewById(R.id.search_layout);
            mainProgressBar = findViewById(R.id.main_progress_bar);
            searchProgressBar = findViewById(R.id.search_progress_bar);
            mainScrollView = findViewById(R.id.main_scroll_view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup view holder and store.
        vh = new ViewHolder();

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.dark_blue_background));
        }

        // Setup listeners.
        vh.actionCardView.setOnClickListener(new CategoryClickListener(ActionListActivity.class));
        vh.strategyCardView.setOnClickListener(new CategoryClickListener(StrategyListActivity.class));
        vh.casualCardView.setOnClickListener(new CategoryClickListener(CasualListActivity.class));
        vh.simulationCardView.setOnClickListener(new CategoryClickListener(SimulationListActivity.class));
        vh.bestsellingButton.setOnClickListener(view -> bestsellingProductsSelected());
        vh.mostViewedButton.setOnClickListener(view -> mostViewedProductsSelected());
        vh.mainSearchView.setOnQueryTextListener(this);

        // Setup recycle view.
        LinearLayoutManager productRecycleListLayout = new LinearLayoutManager (this);
        vh.productListView.setLayoutManager(productRecycleListLayout);
        vh.productListView.setNestedScrollingEnabled(false);

        // Setup recycle view.
        LinearLayoutManager searchProductRecycleListLayout = new LinearLayoutManager (this);
        vh.searchListView.setLayoutManager(searchProductRecycleListLayout);
        vh.searchListView.setNestedScrollingEnabled(false);



        bestsellingProductsSelected();
    }

    /**
     * Highlights the bestselling button and sets
     * the recycle view contents to bestselling products.
     */
    private void bestsellingProductsSelected() {
        vh.bestsellingButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_background));
        vh.mostViewedButton.setBackgroundColor(Color.TRANSPARENT);
        vh.mainProgressBar.setVisibility(View.VISIBLE);
        vh.productListView.setVisibility(View.INVISIBLE);
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
        vh.mainProgressBar.setVisibility(View.VISIBLE);
        vh.productListView.setVisibility(View.INVISIBLE);
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
        adapter.setClickListener(new MainItemAdaptor.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detailsOpenIntent = new Intent(view.getContext(), DetailsActivity.class);
                Product product = productList.get(position);
                detailsOpenIntent.putExtra("Product", product);
                startActivityForResult(detailsOpenIntent, 1 );
            }
        });
        vh.productListView.setAdapter(adapter);
        vh.mainProgressBar.setVisibility(View.GONE);
        vh.productListView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.length() > 0) {
            vh.searchProgressBar.setVisibility(View.VISIBLE);
        } else {
            vh.searchLayout.setVisibility(View.GONE);
            vh.mainScrollView.setVisibility(View.VISIBLE);
            vh.searchProgressBar.setVisibility(View.GONE);
            return true;
        }
        searchResultList = QueryHandler.searchQuery(query, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                vh.searchProgressBar.setVisibility(View.GONE);
                propagateSearchAdapter();
            }
        });
        return true;
    }

    private void propagateSearchAdapter() {
        List<Product> searchList = new ArrayList<>(searchResultList);
        MainItemAdaptor adapter = new MainItemAdaptor(this, searchList);
        adapter.setClickListener(new MainItemAdaptor.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detailsOpenIntent = new Intent(view.getContext(), DetailsActivity.class);
                Product product = searchList.get(position);
                detailsOpenIntent.putExtra("Product", product);
                startActivityForResult(detailsOpenIntent, 2);
            }
        });
        vh.searchListView.setAdapter(adapter);
        vh.searchLayout.setVisibility(View.VISIBLE);
        vh.mainScrollView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (vh.searchLayout.getVisibility() == View.VISIBLE) {
            vh.searchLayout.setVisibility(View.GONE);
            vh.mainScrollView.setVisibility(View.VISIBLE);
            vh.mainScrollView.scrollTo(0,0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Product callBackProduct = data.getParcelableExtra("Product");

        List<Product> copy = new ArrayList<>(productList);
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if ((int)product.getID() == callBackProduct.getID()) {
                copy.remove(i);
                copy.add(i, callBackProduct);
                break;
            }
        }
        productList = copy;
        propagateAdapter();

        if (searchResultList == null) {
            return;
        }

        copy = new ArrayList<>(searchResultList);
        for (int i = 0; i < searchResultList.size(); i++) {
            Product product = searchResultList.get(i);
            if ((int)product.getID() == callBackProduct.getID()) {
                copy.remove(i);
                copy.add(i, callBackProduct);
                break;
            }
        }
        searchResultList = copy;
        propagateSearchAdapter();

        if (requestCode == 1) {
            vh.searchLayout.setVisibility(View.GONE);
            vh.mainScrollView.setVisibility(View.VISIBLE);
            vh.mainScrollView.scrollTo(0,0);
        }
        vh.mainScrollView.requestFocus();
    }
}