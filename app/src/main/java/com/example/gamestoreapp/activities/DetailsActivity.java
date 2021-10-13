package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.implementation.Game;
import com.example.gamestoreapp.implementation.GameProduct;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private ViewHolder vh;
    private GameProduct gameProduct;
    private int imageCounter;
    private List<String> images;
    private GestureDetector gestureDetector;

    /**
     * An internal class used to hold all of the views for this activity
     */
    private class ViewHolder{
        ImageView gameIcon;
        TextView gameName, studioName, viewCount, soldCount, detailsText;
        Button purchaseButton;
        LinearLayout imageSwitcherButtonHolder;
        ImageSwitcher gameImageSwitcher;

        public ViewHolder(){
            gameIcon = findViewById(R.id.gameIcon);
            gameName = findViewById(R.id.gameName);
            studioName = findViewById(R.id.studioName);
            viewCount = findViewById(R.id.viewCount);
            soldCount = findViewById(R.id.soldCount);
            detailsText = findViewById(R.id.detailsText);
            purchaseButton = findViewById(R.id.purchaseButton);
            gameImageSwitcher = findViewById(R.id.gameImageSwitcher);
            imageSwitcherButtonHolder = findViewById(R.id.imageSwitcherButtonHolder);
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
        setContentView(R.layout.activity_details);

        gameProduct = getIntent().getParcelableExtra("Product");

        //Set up a view holder, containing references to all of the views in the activity
        vh = new ViewHolder();

        //Set the text for all of the labels
        vh.detailsText.setText(gameProduct.getItem().getDescription());
        vh.gameName.setText(gameProduct.getItem().getName());
        vh.studioName.setText(gameProduct.getItem().getStudioName());
        vh.purchaseButton.setText(gameProduct.getCostAsString());
        vh.soldCount.setText(String.valueOf(gameProduct.getAmountSold()));
        vh.viewCount.setText(String.valueOf(gameProduct.getViewCount()));
        //may end up rounding these and then converting to "200k +" format

        //Get the Image resource from the product and set it for the game icon view
        vh.gameIcon.setImageResource(this.getResources().getIdentifier(gameProduct.getItem().getIconImageName(),"drawable", "com.example.gamestoreapp"));

        //Set up the storage for the image switcher image as well as what the current image is
        images = new ArrayList<String>();
        images.addAll(gameProduct.getItem().getImagesNames());
        imageCounter = 0;

        if(images.size() == 0){
            return;
        }

        //Set up the factory to switch images in the image switcher
        vh.gameImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(getApplicationContext());
            }
        });

        //Put the first image in the image switcher
        vh.gameImageSwitcher.setImageResource(getApplicationContext().getResources().getIdentifier(images.get(imageCounter),"drawable", "com.example.gamestoreapp"));

        //Set up the detection for swipes, triggered when the image switcher receives a on touch event
        gestureDetector = new GestureDetector(this, new SwitcherGestureDetector());
        vh.gameImageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        //Set up width, height and padding for the buttons in dp and then converted to pixels so that it can be made on runtime.
        int pixelHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        int pixelWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
        int padding = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));

        for (int i = 0; i < images.size(); i++){
            Button button = new Button(getApplicationContext());
            vh.imageSwitcherButtonHolder.addView(button);
            button.getLayoutParams().height=pixelHeight;
            button.getLayoutParams().width=pixelWidth;
            button.setId(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vh.gameImageSwitcher.setImageResource(getApplicationContext().getResources().getIdentifier(images.get(view.getId()), "drawable", "com.example.gamestoreapp"));
                    imageCounter = view.getId();
                }
            });

        }
    }

    /**
     * An internal class extending SimpleOnGestureListener, used to provide the logic for
     * checking whether a specific onTouch event is considered a swipe in a horizontal direction
     */
    class SwitcherGestureDetector extends SimpleOnGestureListener{
        //Needs to be true in order to show that the onDown event is being managed by this detector
        @Override
        public boolean onDown(MotionEvent motionEvent){
            return true;
        }

        //Called when a fling event occurs, checks what direction it is in and switches the image accordingly
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
            //If the movement was more horizontal than vertical, do not change the image.
            if (Math.abs(velocityY) > Math.abs(velocityX)){
                return false;
            }
            //If movement was to the left, switch to the next image, if this was the last image, go to the first instead
            if (velocityX < 0){
                imageCounter ++;
                if(imageCounter >= images.size()){
                    imageCounter = 0;
                }
                vh.gameImageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                vh.gameImageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
            }
            //Otherwise, switch to the previous image, if this was the first image, go to the last instead
            else {
                imageCounter --;
                if (imageCounter < 0){
                    imageCounter = images.size() - 1;
                }
                vh.gameImageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
                vh.gameImageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
            }
            //Sets the new image according to the change in imageCounter decided above
            vh.gameImageSwitcher.setImageResource(getApplicationContext().getResources().getIdentifier(images.get(imageCounter),"drawable", "com.example.gamestoreapp"));
            return true;
        }
    }
}