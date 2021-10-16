package com.example.gamestoreapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.ProductAdaptor;
import com.example.gamestoreapp.data.QueryHandler;
import com.example.gamestoreapp.implementation.Game;
import com.example.gamestoreapp.implementation.GameProduct;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.ListActivity;
import com.example.gamestoreapp.interfaces.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class CategoryListActivity  extends AppCompatActivity implements ListActivity {

    protected String categoryName;
    protected ViewHolder vh;
    protected List<String> imageNames;
    private int currentImage;
    private List<Product> productList;
    private GestureDetector gestureDetector;

    private int expectedProductCount;

    protected class ViewHolder {
        ListView listView;
        ProgressBar progressBar;
        LinearLayout layout;
        ImageSwitcher categoryImageSwitcher;
        TextView nextImageIcon;
        TextView prevImageIcon;
        List<Button> imageButtons = new ArrayList<Button>();
        LinearLayout imageButtonHolder;

        public ViewHolder(ListView listView, ProgressBar progressBar, LinearLayout layout,
                          ImageSwitcher categoryImageSwitcher, TextView nextImageIcon, TextView prevImageIcon, LinearLayout imageButtonHolder) {
            this.listView = listView;
            this.progressBar = progressBar;
            this.layout = layout;
            this.categoryImageSwitcher = categoryImageSwitcher;
            this.nextImageIcon = nextImageIcon;
            this.prevImageIcon = prevImageIcon;
            this.imageButtonHolder = imageButtonHolder;
        }
    }

    @Override
    public void loadCategory() {

        imageNames = QueryHandler.fetchCategoryImageNames(categoryName, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                if (!imageNames.isEmpty()) {
                    setCategoryImage(0);
                    setupImageSwitchButtons();
                }

            }
        });

        productList = QueryHandler.queryCategoryCollection(categoryName, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                propagateAdapter();
            }
        });

        vh.nextImageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryImage(currentImage + 1);
            }
        });

        vh.prevImageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryImage(currentImage - 1);
            }
        });



    }

    private void setupImageSwitchButtons() {

        vh.categoryImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(getApplicationContext());
            }
        });

        vh.categoryImageSwitcher.setImageResource(getBaseContext().getResources().getIdentifier(imageNames.get(0),"drawable", "com.example.gamestoreapp"));
        currentImage = 0;

        //Set up the detection for swipes, triggered when the image switcher receives a on touch event
        gestureDetector = new GestureDetector(this, new CategoryListActivity.SwitcherGestureDetector());
        vh.categoryImageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        //Set up width, height and padding for the buttons in dp and then converted to pixels so that it can be made on runtime.
        int pixelHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        int pixelWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));

        for(int i = 0; i < imageNames.size(); i++){
            Button button = new Button(getApplicationContext());
            vh.imageButtonHolder.addView(button);
            button.getLayoutParams().height=pixelHeight;
            button.getLayoutParams().width=pixelWidth;
            button.setId(i);
            vh.imageButtons.add(button);

            button.getBackground().setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newImage = Math.abs(view.getId());
                    setCategoryImage(newImage);
                }
            });
        }
    }

    @Override
    public void goToNextCategory() {

    }

    @Override
    public void goToPreviousCategory() {

    }

    private void propagateAdapter() {

        if (productList.size() > 0) {
            Log.i("Getting Products", "Success");
            vh.progressBar.setVisibility(View.GONE);
        } else {
            Toast.makeText(getBaseContext(), "Products Collection was empty!", Toast.LENGTH_LONG).show();
        }

        ProductAdaptor itemsAdapter = new ProductAdaptor(this, R.layout.game_list_view_item,
                productList);
        vh.listView.setAdapter(itemsAdapter);
        vh.listView.setVisibility(View.VISIBLE);
    }

    private boolean setCategoryImage(int newImageIndex) {
        /**
        if (imageNames.size() == 0) {
            return;
        } else if (i >= imageNames.size()) {
            i = 0;
        } else if (i < 0) {
            i = imageNames.size()-1;
        }
        int image = getBaseContext().getResources().getIdentifier(
                imageNames.get(i), "drawable",
                getBaseContext().getPackageName());
        vh.categoryImageView.setImageResource(image);

        currentImage = i;
        **/

        // Enforce wrapping on image display
        if(newImageIndex >= imageNames.size()){
            newImageIndex = 0;
            vh.categoryImageSwitcher.setInAnimation(getBaseContext(), R.anim.slide_in_right);
            vh.categoryImageSwitcher.setOutAnimation(getBaseContext(), R.anim.slide_out_left);
        } else if (newImageIndex < 0){
            newImageIndex = imageNames.size() - 1;
            vh.categoryImageSwitcher.setInAnimation(getBaseContext(), R.anim.slide_in_left);
            vh.categoryImageSwitcher.setOutAnimation(getBaseContext(), R.anim.slide_out_right);
        } else
            // check if image is already on display
            if (newImageIndex == currentImage) {
                return false;
                // otherwise play animation and change image
            } else if (currentImage < newImageIndex) {
                vh.categoryImageSwitcher.setInAnimation(getBaseContext(), R.anim.slide_in_right);
                vh.categoryImageSwitcher.setOutAnimation(getBaseContext(), R.anim.slide_out_left);
            } else {
                vh.categoryImageSwitcher.setInAnimation(getBaseContext(), R.anim.slide_in_left);
                vh.categoryImageSwitcher.setOutAnimation(getBaseContext(), R.anim.slide_out_right);
            }
        vh.imageButtons.get(newImageIndex).getBackground().setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.MULTIPLY);
        vh.imageButtons.get(currentImage).getBackground().setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        currentImage = newImageIndex;
        vh.categoryImageSwitcher.setImageResource(getBaseContext().
                getResources().getIdentifier(imageNames.get(currentImage),
                "drawable", "com.example.gamestoreapp"));
        return true;




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Product callBackProduct = data.getParcelableExtra("Product");
        super.onActivityResult(requestCode, resultCode, data);
        List<Product> copy = new ArrayList<>(productList);
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if ((int)product.getID() == callBackProduct.getID()) {
                copy.remove(i);
                copy.add(i, callBackProduct);
                break;
            }
        }
        productList = copy;
        propagateAdapter();
    }

    /**
     * An internal class extending SimpleOnGestureListener, used to provide the logic for
     * checking whether a specific onTouch event is considered a swipe in a horizontal direction
     */
    class SwitcherGestureDetector extends GestureDetector.SimpleOnGestureListener {
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
                newImage = currentImage + 1;
            }
            //Otherwise, switch to the previous image, if this was the first image, go to the last instead
            else {
                newImage = currentImage -1;
            }
            //Sets the new image according to the change in imageCounter decided above
            setCategoryImage(newImage);
            return true;
        }
    }
}
