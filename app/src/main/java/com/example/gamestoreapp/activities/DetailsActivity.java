package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.example.gamestoreapp.data.QueryHandler;
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
        List<Button> imageButtons = new ArrayList<Button>();

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
        //may end up rounding these and then converting to "200k +" format

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

        for (int i = 0; i < images.size(); i++){
            Button button = new Button(getApplicationContext());
            vh.imageSwitcherButtonHolder.addView(button);
            button.getLayoutParams().height=pixelHeight;
            button.getLayoutParams().width=pixelWidth;
            button.setId(i);
            vh.imageButtons.add(button);
            button.getBackground().setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newImage = Math.abs(view.getId());
                    changeImage(newImage);
                }
            });

        }
        vh.imageButtons.get(imageCounter).getBackground().setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.MULTIPLY);
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
            int newImage;
            //If movement was to the left, switch to the next image, if this was the last image, go to the first instead
            if (velocityX < 0){
                newImage = imageCounter + 1;
            }
            //Otherwise, switch to the previous image, if this was the first image, go to the last instead
            else {
                newImage = imageCounter -1;
            }
            //Sets the new image according to the change in imageCounter decided above
            changeImage(newImage);
            return true;
        }
    }

    /**
     * Change the image on display, and play a smooth animation.
     * @param newImageIndex id of new image to display
     * @return true if image was changed
     */
    private boolean changeImage(int newImageIndex) {

        // Enforce wrapping on image display
        if(newImageIndex >= images.size()){
            newImageIndex = 0;
            vh.gameImageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
            vh.gameImageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
        } else if (newImageIndex < 0){
            newImageIndex = images.size() - 1;
            vh.gameImageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
            vh.gameImageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
        } else
        // check if image is already on display
        if (newImageIndex == imageCounter) {
            return false;
        // otherwise play animation and change image
        } else if (imageCounter < newImageIndex) {
            vh.gameImageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
            vh.gameImageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
        } else {
            vh.gameImageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
            vh.gameImageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
        }
        vh.imageButtons.get(newImageIndex).getBackground().setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.MULTIPLY);
        vh.imageButtons.get(imageCounter).getBackground().setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        imageCounter = newImageIndex;
        vh.gameImageSwitcher.setImageResource(getApplicationContext().
                getResources().getIdentifier(images.get(imageCounter),
                "drawable", "com.example.gamestoreapp"));
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Product", gameProduct);
        setResult(Activity.RESULT_OK, returnIntent);
        QueryHandler.updateSalesData(gameProduct);
        super.onBackPressed();
    }
}