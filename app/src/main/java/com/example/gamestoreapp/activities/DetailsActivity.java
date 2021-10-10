package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.implementation.Game;
import com.example.gamestoreapp.implementation.GameProduct;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ViewHolder vh;
    private GameProduct gameProduct; //This will get received from the intent that switches the screen to this activity

    private class ViewHolder{
        ImageView gameIcon;
        TextView gameName, studioName, viewCount, soldCount, detailsText;
        Button purchaseButton, imageSwitchButton1, imageSwitchButton2, imageSwitchButton3, imageSwitchButton4;
        ImageSwitcher gameImageSwitcher;

        public ViewHolder(){
            gameIcon = findViewById(R.id.gameIcon);
            gameName = findViewById(R.id.gameName);
            studioName = findViewById(R.id.studioName);
            viewCount = findViewById(R.id.viewCount);
            soldCount = findViewById(R.id.soldCount);
            detailsText = findViewById(R.id.detailsText);
            purchaseButton = findViewById(R.id.purchaseButton);
            imageSwitchButton1 = findViewById(R.id.imageSwitchButton1);
            imageSwitchButton2 = findViewById(R.id.imageSwitchButton2);
            imageSwitchButton3 = findViewById(R.id.imageSwitchButton3);
            imageSwitchButton4 = findViewById(R.id.imageSwitchButton4);
            gameImageSwitcher = findViewById(R.id.gameImageSwitcher);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        gameProduct = getIntent().getParcelableExtra("Product");

        vh = new ViewHolder();

        vh.detailsText.setText(gameProduct.getItem().getDescription());
        vh.gameName.setText(gameProduct.getItem().getName());
        vh.studioName.setText(gameProduct.getItem().getStudioName());

        vh.soldCount.setText(String.valueOf(gameProduct.getAmountSold()));
        vh.viewCount.setText(String.valueOf(gameProduct.getViewCount()));
        //may end up rounding these and then converting to "200k +" format

        vh.purchaseButton.setText(gameProduct.getCostAsString());
        vh.gameIcon.setImageResource(this.getResources().getIdentifier(gameProduct.getItem().getIconImageName(),"drawable", "com.example.gamestoreapp"));
    }
}