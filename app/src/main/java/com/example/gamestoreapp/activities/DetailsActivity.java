package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamestoreapp.R;

public class DetailsActivity extends AppCompatActivity {

    private ViewHolder vh;

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

        vh = new ViewHolder();
    }
}