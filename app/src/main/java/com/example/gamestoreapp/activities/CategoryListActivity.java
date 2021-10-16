package com.example.gamestoreapp.activities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private int expectedProductCount;

    protected class ViewHolder {
        ListView listView;
        ProgressBar progressBar;
        LinearLayout layout;
        ImageView categoryImageView;
        TextView nextImageIcon;
        TextView prevImageIcon;

        public ViewHolder(ListView listView, ProgressBar progressBar, LinearLayout layout,
                          ImageView categoryImageView, TextView nextImageIcon, TextView prevImageIcon) {
            this.listView = listView;
            this.progressBar = progressBar;
            this.layout = layout;
            this.categoryImageView = categoryImageView;
            this.nextImageIcon = nextImageIcon;
            this.prevImageIcon = prevImageIcon;
        }
    }

    @Override
    public void loadCategory() {

        imageNames = QueryHandler.fetchCategoryImageNames(categoryName, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                if (!imageNames.isEmpty()) {
                    setCategoryImage(0);
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

    private void setCategoryImage(int i) {
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
}
