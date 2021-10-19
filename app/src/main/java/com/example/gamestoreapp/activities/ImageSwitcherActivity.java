package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.example.gamestoreapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of an activity featuring an animated image switcher with dynamic buttons.
 */
public abstract class ImageSwitcherActivity extends AppCompatActivity {
    protected List<String> imageNames;
    private int currentImageIndex;
    protected ImageSwitcherViewHolder imageViewHolder;
    private GestureDetector gestureDetector;

    protected class ImageSwitcherViewHolder {
        List<Button> buttonList = new ArrayList<Button>();
        LinearLayout buttonHolder;
        ImageSwitcher imageSwitcher;

        public ImageSwitcherViewHolder(LinearLayout buttonHolder, ImageSwitcher imageSwitcher) {
            this.buttonHolder = buttonHolder;
            this.imageSwitcher = imageSwitcher;
        }

    }

    /**
     * Change the image on display, and play a smooth animation.
     * @param newImageIndex id of new image to display
     * @return true if image was changed
     */
    private boolean changeImage(int newImageIndex) {

        // Enforce wrapping on image display
        if(newImageIndex >= imageNames.size()){
            newImageIndex = 0;
            imageViewHolder.imageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
            imageViewHolder.imageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
        } else if (newImageIndex < 0){
            newImageIndex = imageNames.size() - 1;
            imageViewHolder.imageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
            imageViewHolder.imageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
        } else
            // check if image is already on display, and do not change image if so
            if (newImageIndex == currentImageIndex) {
                return false;
                // otherwise play animation and change image
            } else if (currentImageIndex < newImageIndex) {
                imageViewHolder.imageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                imageViewHolder.imageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
            } else {
                imageViewHolder.imageSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
                imageViewHolder.imageSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
            }
            // update colours of buttons to reflect whether they are currently selected
        imageViewHolder.buttonList.get(newImageIndex).getBackground().setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.MULTIPLY);
        imageViewHolder.buttonList.get(currentImageIndex).getBackground().setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        currentImageIndex = newImageIndex;
        // update the image to reflect the new index
        imageViewHolder.imageSwitcher.setImageResource(getApplicationContext().
                getResources().getIdentifier(imageNames.get(currentImageIndex),
                "drawable", "com.example.gamestoreapp"));
        return true;
    }

    protected void initialiseButtons() {

        if (imageNames.size() <= 0) {
            return;
        }

        //Set up the factory to switch images in the image switcher
        imageViewHolder.imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(getApplicationContext());
            }
        });

        //Put the first image in the image switcher
        imageViewHolder.imageSwitcher.setImageResource(getApplicationContext().
                getResources().getIdentifier(imageNames.
                get(currentImageIndex),"drawable", "com.example.gamestoreapp"));

        //Set up the detection for swipes, triggered when the image switcher receives a on touch event
        gestureDetector = new GestureDetector(this, new SwitcherGestureDetector());
        imageViewHolder.imageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        //Set up width, height and padding for the buttons in dp and then converted to pixels so that it can be made on runtime.
        int pixelHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        int pixelWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
        int padding = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));

        // dynamically add buttons to the layout for each image
        for (int i = 0; i < imageNames.size(); i++){
            Button button = new Button(getApplicationContext());
            imageViewHolder.buttonHolder.addView(button);
            button.getLayoutParams().height=pixelHeight;
            button.getLayoutParams().width=pixelWidth;
            button.setId(i);
            imageViewHolder.buttonList.add(button);
            button.getBackground().setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newImage = Math.abs(view.getId());
                    changeImage(newImage);
                }
            });

        }
        // the currently selected button should be highlighted in white
        imageViewHolder.buttonList.get(currentImageIndex).getBackground().setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.MULTIPLY);
    }

    /**
     * An internal class extending SimpleOnGestureListener, used to provide the logic for
     * checking whether a specific onTouch event is considered a swipe in a horizontal direction
     */
    private class SwitcherGestureDetector extends GestureDetector.SimpleOnGestureListener {
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
                newImage = currentImageIndex + 1;
            }
            //Otherwise, switch to the previous image, if this was the first image, go to the last instead
            else {
                newImage = currentImageIndex - 1;
            }
            //Sets the new image according to the change in imageCounter decided above
            changeImage(newImage);
            return true;
        }
    }


}