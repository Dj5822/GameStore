package com.example.gamestoreapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.data.QueryHandler;
import com.example.gamestoreapp.model.Product;
import androidx.transition.*;

import java.util.ArrayList;

import com.google.android.material.transition.platform.MaterialContainerTransform;

/**
 * Activity that shows detailed information about a particular product
 */
public class DetailsActivity extends ImageSwitcherActivity {

    private ViewHolder vh;
    private Product gameProduct;

    /**
     * An internal class used to hold all of the views for this activity
     */
    private class ViewHolder{
        ImageView gameIcon;
        TextView gameName, studioName, viewCount, soldCount, detailsText;
        Button purchaseButton;
        ScrollView scrollView;

        public ViewHolder(){
            gameIcon = findViewById(R.id.gameIcon);
            gameName = findViewById(R.id.gameName);
            studioName = findViewById(R.id.studioName);
            viewCount = findViewById(R.id.viewCount);
            soldCount = findViewById(R.id.soldCount);
            detailsText = findViewById(R.id.detailsText);
            purchaseButton = findViewById(R.id.purchaseButton);
            scrollView = findViewById(R.id.scroll_details);
        }
    }

    /**
     * Manages the initialisation of the Details Activity, run whenever this activity is loaded.
     * This method sets up references to all of the elements in the activity as well as setting
     * up the correct images and text relevant to the product to be displayed. A reference to the
     * correct product is passed through the intent that loads this activity.
     *
     * @param savedInstanceState The save state to enable going back to the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MaterialContainerTransform();
        setContentView(R.layout.activity_details);

        // Used to modify navigation bar color.
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.dark_blue_background));
        }

        gameProduct = getIntent().getParcelableExtra("Product");

        // Increment viewCount of gameProduct
        gameProduct.view();

        //Set up a view holder, containing references to all of the views in the activity
        vh = new ViewHolder();

        //Set the text for all of the labels
        vh.detailsText.setText(gameProduct.getItem().getDescription());
        vh.gameName.setText(gameProduct.getItem().getName());
        vh.studioName.setText(gameProduct.getItem().getStudioName());
        vh.purchaseButton.setText(gameProduct.getCostAsString());
        vh.soldCount.setText(String.valueOf(gameProduct.getAmountSold()));
        vh.viewCount.setText(String.valueOf(gameProduct.getViewCount()));

        // Buy game when purchase button is clicked
        vh.purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameProduct.buy();
                vh.soldCount.setText(String.valueOf(gameProduct.getAmountSold()));
            }
        });

        //Get the Image resource from the product and set it for the game icon view
        vh.gameIcon.setImageResource(this.getResources().getIdentifier(gameProduct.getItem().getIconImageName(),"drawable", "com.example.gamestoreapp"));

        //Set up the storage for the image switcher image as well as what the current image is
        imageNames = new ArrayList<String>();
        imageNames.addAll(gameProduct.getItem().getImagesNames());
        imageViewHolder = new ImageSwitcherViewHolder(
                findViewById(R.id.imageSwitcherButtonHolder),
                findViewById(R.id.gameImageSwitcher));
        initialiseButtons();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Product", gameProduct);
        setResult(Activity.RESULT_OK, returnIntent);
        QueryHandler.updateSalesData(gameProduct);
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}