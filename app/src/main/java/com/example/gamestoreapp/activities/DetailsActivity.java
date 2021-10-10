package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.implementation.Game;
import com.example.gamestoreapp.implementation.GameProduct;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private ViewHolder vh;
    private GameProduct gameProduct; //This will get received from the intent that switches the screen to this activity
    private int imageCounter;
    private List<String> images;

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

        images = new ArrayList<String>();
        images.addAll(gameProduct.getItem().getImagesNames());

        System.out.println("printing image names:");
        for (String name : images){
            System.out.println(name);
        }

        imageCounter = 0;

        System.out.println(images.get(0));
        System.out.println(this.getResources().getIdentifier(images.get(0),"drawable", "com.example.gamestoreapp"));
        System.out.println("switcher id: " + vh.gameImageSwitcher.getId());
        //vh.gameImageSwitcher.setImageResource(this.getResources().getIdentifier(images.get(0),"drawable", "com.example.gamestoreapp"));

        vh.gameImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(getApplicationContext().getResources().getIdentifier(images.get(imageCounter),"drawable", "com.example.gamestoreapp"));

                imageCounter++;
                if(imageCounter >= images.size()){
                    imageCounter = 0;
                }
                return imageView;
            }
        });
        vh.gameImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        vh.gameImageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);

        vh.purchaseButton.setText(gameProduct.getCostAsString());
        vh.gameIcon.setImageResource(this.getResources().getIdentifier(gameProduct.getItem().getIconImageName(),"drawable", "com.example.gamestoreapp"));
    }
}