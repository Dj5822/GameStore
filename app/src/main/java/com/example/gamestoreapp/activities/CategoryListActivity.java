package com.example.gamestoreapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import io.grpc.okhttp.internal.framed.Header;

public abstract class CategoryListActivity  extends ImageSwitcherActivity implements ListActivity {

    protected String categoryName;
    protected ViewHolder vh;
    private List<Product> productList;
    private OrientationEventListener orientationEventListener;

    protected class ViewHolder {
        ListView listView;
        ProgressBar progressBar;
        LinearLayout layout, header;

        public ViewHolder(ListView listView, ProgressBar progressBar, LinearLayout layout, LinearLayout header) {
            this.listView = listView;
            this.progressBar = progressBar;
            this.layout = layout;
            this.header = header;
        }
    }

    @Override
    public void loadCategory() {
        imageViewHolder = new ImageSwitcherViewHolder(
                findViewById(R.id.categoryImageSwitcherButtonHolder),
                findViewById(R.id.categoryImageSwitcher)
                );

        imageNames = QueryHandler.fetchCategoryImageNames(categoryName, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                if (!imageNames.isEmpty()) {
                    initialiseButtons();
                }
            }
        });

        productList = QueryHandler.queryCategoryCollection(categoryName, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                propagateAdapter();
            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        vh.header.setVisibility(
                newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        vh.header.setVisibility(
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? View.GONE : View.VISIBLE);
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
        vh.listView.setAdapter(getAdaptor());
        vh.listView.setVisibility(View.VISIBLE);
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

    protected ProductAdaptor getAdaptor() {
        ProductAdaptor itemsAdapter = new ProductAdaptor(this, R.layout.game_list_view_item,
                productList, categoryName);
        return itemsAdapter;
    }

    protected List<Product> getProductList() {
        return productList;
    }
}
