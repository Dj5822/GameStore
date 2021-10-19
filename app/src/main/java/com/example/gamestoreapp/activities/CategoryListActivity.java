package com.example.gamestoreapp.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.ProductAdaptor;
import com.example.gamestoreapp.data.QueryHandler;
import com.example.gamestoreapp.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of category activity that has a list of products in the category
 * and a header image switcher.
 */
public abstract class CategoryListActivity  extends ImageSwitcherActivity {

    protected String categoryName;
    protected ViewHolder vh;
    private List<Product> productList;

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

    /**
     * Initialise views with data from queries
     */
    public void loadCategory() {

        imageViewHolder = new ImageSwitcherViewHolder(
                findViewById(R.id.categoryImageSwitcherButtonHolder),
                findViewById(R.id.categoryImageSwitcher)
                );

        // Query for the names of header images
        imageNames = QueryHandler.fetchCategoryImageNames(categoryName, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                if (!imageNames.isEmpty()) {
                    initialiseButtons();
                }
            }
        });

        // Query for the products in the category
        productList = QueryHandler.queryCategoryCollection(categoryName, new QueryHandler.QueryListener() {
            @Override
            public void OnQueryComplete() {
                propagateAdapter();
            }
        });

    }

    @Override
    /**
     * Hide header image when switching to landscape mode
     */
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        vh.header.setVisibility(
                newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? View.GONE : View.VISIBLE);
    }

    @Override
    /**
     * Hide header image when opened in landscape mode
     */
    protected void onStart() {
        super.onStart();
        vh.header.setVisibility(
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? View.GONE : View.VISIBLE);
    }

    /**
     * Update the adapter for the list of products
     */
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

    /**
     * Called when returning from DetailsActivity. Replaces the relevant product instance with the
     * updated (viewCount and amountSold changed) instance from the DetailsActivity.
     * @param requestCode code detailsActivity was called with
     * @param resultCode status detailsActivity returned with
     * @param data Intent from detailsActivity containing updated product instance.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Get updated product instance
        Product callBackProduct = data.getParcelableExtra("Product");
        super.onActivityResult(requestCode, resultCode, data);
        List<Product> copy = new ArrayList<>(productList);
        // Replace old instance with updated instance
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
     * Can be overridden by inheriting activities to use a different xml layout
     * @return The adaptor to use
     */
    protected ProductAdaptor getAdaptor() {
        ProductAdaptor itemsAdapter = new ProductAdaptor(this, R.layout.game_list_view_item,
                productList, categoryName);
        return itemsAdapter;
    }

    /**
     * @return current list of product instances used to create the adaptor.
     */
    protected List<Product> getProductList() {
        return productList;
    }
}
