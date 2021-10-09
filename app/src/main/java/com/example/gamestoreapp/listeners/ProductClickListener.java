package com.example.gamestoreapp.listeners;

import android.content.Intent;
import android.view.View;

import com.example.gamestoreapp.activities.DetailsActivity;
import com.example.gamestoreapp.implementation.GameProduct;
import com.example.gamestoreapp.interfaces.Product;

public class ProductClickListener implements View.OnClickListener {

    private Product product;

    public ProductClickListener(Product product) {
        this.product = product;
    }

    @Override
    public void onClick(View view) {
        Intent detailsOpenIntent = new Intent(view.getContext(), DetailsActivity.class);
        detailsOpenIntent.putExtra("Product", product);
        view.getContext().startActivity(detailsOpenIntent);
    }
}
