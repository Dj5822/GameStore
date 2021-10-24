package com.example.gamestoreapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.ProductAdaptor;

/**
 * Extension of CategoryListActivity used for the action category page
 */
public class ActionListActivity extends CategoryListActivity {

    /**
     * Manages this initialisation of the ActionListActivity, run whenever this activity
     * is loaded. This method sets up all of the references to elements within the activity
     * and sets appropriate values for labels within this activity
     *
     * @param savedInstanceState The save state to enable going back to the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Used to modify navigation bar color.
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.dark_blue_background));
        }

        ProgressBar progressBar = findViewById(R.id.load_progressbar);
        ListView listView = findViewById(R.id.list_view);
        LinearLayout actionLayout = findViewById(R.id.list_layout);
        LinearLayout header = findViewById(R.id.category_header);

        TextView title = findViewById(R.id.category_title);
        title.setText(R.string.action);

        vh = new ViewHolder(listView, progressBar, actionLayout, header);

        categoryName = "action";

        loadCategory();
    }

    /**
     * Gets the adaptor for this activity
     *
     * @return the adaptor to use
     */
    @Override
    protected ProductAdaptor getAdaptor() {
        ProductAdaptor itemsAdapter = new ProductAdaptor(this, R.layout.action_game_list_view_item,
                getProductList(), categoryName);
        return itemsAdapter;
    }
}