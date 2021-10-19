package com.example.gamestoreapp.listeners;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamestoreapp.activities.DetailsActivity;
import com.example.gamestoreapp.model.Product;

public class ProductClickListener implements View.OnClickListener {

    private Product product;

    public ProductClickListener(Product product) {
        this.product = product;
    }

    @Override
    public void onClick(View view) {
        Intent detailsOpenIntent = new Intent(view.getContext(), DetailsActivity.class);
        detailsOpenIntent.putExtra("Product", product);
        AppCompatActivity activity = unwrap(view.getContext());
        activity.startActivityForResult(detailsOpenIntent, (int) product.getID());
    }

    private static AppCompatActivity unwrap(Context context) {
        while (!(context instanceof AppCompatActivity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (AppCompatActivity) context;
    }
}
