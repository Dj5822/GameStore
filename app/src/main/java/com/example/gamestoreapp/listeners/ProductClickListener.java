package com.example.gamestoreapp.listeners;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamestoreapp.activities.DetailsActivity;
import com.example.gamestoreapp.model.Product;

/**
 * Listener that opens product details when clicked
 */
public class ProductClickListener implements View.OnClickListener {

    private Product product;

    public ProductClickListener(Product product) {
        this.product = product;
    }

    @Override
    /**
     * Open details activity, while passing a product instance to display details for
     */
    public void onClick(View view) {
        Intent detailsOpenIntent = new Intent(view.getContext(), DetailsActivity.class);
        detailsOpenIntent.putExtra("Product", product);
        AppCompatActivity activity = unwrap(view.getContext());
        activity.startActivityForResult(detailsOpenIntent, (int) product.getID());
    }

    /**
     * Get the most senior ancestor context of a wrapper
     * @param context context to unwrap
     * @return unwrapped context as an app compat activity
     */
    private static AppCompatActivity unwrap(Context context) {
        while (!(context instanceof AppCompatActivity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (AppCompatActivity) context;
    }
}
