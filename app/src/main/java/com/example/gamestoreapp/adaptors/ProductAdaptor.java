package com.example.gamestoreapp.adaptors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.implementation.GameProduct;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.Product;
import com.example.gamestoreapp.listeners.ProductClickListener;

import java.util.List;

public class ProductAdaptor extends ArrayAdapter {

    private class ViewHolder {
        TextView productNameView, productPriceView;
        ImageView productIconView;
        CardView listItemView;

        public ViewHolder(View currentListViewItem) {
            listItemView = currentListViewItem.findViewById(R.id.list_view_item);
            productIconView = currentListViewItem.findViewById(R.id.product_icon_view);
            productNameView = currentListViewItem.findViewById(R.id.product_title_view);
            productPriceView = currentListViewItem.findViewById(R.id.product_price_view);
        }

        public ViewHolder(ViewHolder viewHolder, View currentListViewItem) {
            listItemView = viewHolder.listItemView;
            productIconView = viewHolder.productIconView;
            productNameView = viewHolder.productNameView;
            productPriceView = viewHolder.productPriceView;
        }
    }

    private class CasualViewHolder extends ViewHolder {
        ImageView mobileIcon;
        public CasualViewHolder(View currentListViewItem) {
            super(currentListViewItem);
            mobileIcon = currentListViewItem.findViewById(R.id.mobile_icon_view);
        }
    }

    private class ActionViewHolder extends ViewHolder {
        ImageView ageRestrictionIcon;
        public ActionViewHolder(View currentListViewItem) {
            super(currentListViewItem);
            ageRestrictionIcon = currentListViewItem.findViewById(R.id.age_restriction_icon_view);
        }
    }

    private int layoutId;
    private List<Product> productList;
    private Context context;
    String categoryName;


    public ProductAdaptor(@NonNull Context context, int resource, @NonNull List objects, String categoryName) {
        super(context, resource, objects);
        layoutId = resource;
        this.context = context;
        productList = objects;
        this.categoryName = categoryName;
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
        vh.productPriceView.setText(currentProduct.getCostAsString());

        // Set OnClick Listener
        currentListViewItem.setOnClickListener(new ProductClickListener(currentProduct));

        Drawable background = vh.listItemView.getBackground();
        background.setAlpha(80);
        switch (categoryName) {

            case "action":
                background.setColorFilter(currentListViewItem.getResources()
                        .getColor(R.color.dark_red), PorterDuff.Mode.MULTIPLY);
                ActionViewHolder avh = new ActionViewHolder(currentListViewItem);

                if (currentItem.getAgeRestriction() >= 18) {
                    int j = context.getResources().getIdentifier(
                            "eighteen", "drawable",
                            context.getPackageName());
                    avh.ageRestrictionIcon.setImageResource(j);

                } else if (currentItem.getAgeRestriction() >= 16) {
                    int j = context.getResources().getIdentifier(
                            "sixteen", "drawable",
                            context.getPackageName());
                    avh.ageRestrictionIcon.setImageResource(j);

                } else {
                    avh.ageRestrictionIcon.setVisibility(View.GONE);
                }
                break;


            case "casual":
                background.setColorFilter(currentListViewItem.getResources()
                        .getColor(R.color.orange), PorterDuff.Mode.MULTIPLY);
                CasualViewHolder cvh = new CasualViewHolder(currentListViewItem);
                if (((GameProduct) currentProduct).isMobile()) {
                    cvh.mobileIcon.setVisibility(View.VISIBLE);
                } else {
                    cvh.mobileIcon.setVisibility(View.GONE);
                }
                break;


            case "simulation":
                background.setColorFilter(currentListViewItem.getResources()
                        .getColor(R.color.deep_blue), PorterDuff.Mode.MULTIPLY);
                break;
            case "strategy":
                background.setColorFilter(currentListViewItem.getResources()
                        .getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
                break;
        }

        return currentListViewItem;
    }
}
