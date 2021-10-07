package com.example.gamestoreapp.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.Product;

import java.util.List;

public class ProductAdaptor extends ArrayAdapter {

    private class ViewHolder {
        TextView productNameView, productPriceView;
        ImageView productIconView;

        public ViewHolder(View currentListViewItem) {
            productIconView = currentListViewItem.findViewById(R.id.product_icon_view);
            productNameView = currentListViewItem.findViewById(R.id.product_title_view);
            productPriceView = currentListViewItem.findViewById(R.id.product_price_view);
        }
    }

    private int layoutId;
    private List<Product> productList;
    private Context context;

    public ProductAdaptor(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        layoutId = resource;
        this.context = context;
        productList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get a reference to the current ListView item
        View currentListViewItem = convertView;

        // Check if the existing view is being reused, otherwise inflate the view
        if (currentListViewItem == null) {
            currentListViewItem = LayoutInflater.from(getContext()).inflate(
                    layoutId, parent, false);
        }

        // Get the Product object for the current position
        Product currentProduct = productList.get(position);
        Item currentItem = currentProduct.getItem();

        // Note: a new ViewHolder object has to be created for each currentListViewItem
        ViewHolder vh = new ViewHolder(currentListViewItem);

        // Set the attributes of list item views

        // Set icon
        int i = context.getResources().getIdentifier(
                currentItem.getIconImageName(), "drawable",
                context.getPackageName());
        vh.productIconView.setImageResource(i);

        // Set Title
        vh.productNameView.setText(currentItem.getName());

        // Set Price
        vh.productPriceView.setText(currentProduct.getCost());

        // TODO: set onclick handler

        return currentListViewItem;
    }
}
