package com.example.gamestoreapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.ProductAdaptor;

public class ActionListActivity extends CategoryListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ProgressBar progressBar = findViewById(R.id.load_progressbar);
        ListView listView = findViewById(R.id.list_view);
        LinearLayout actionLayout = findViewById(R.id.list_layout);

        TextView title = findViewById(R.id.category_title);
        title.setText(R.string.action);

        vh = new ViewHolder(listView, progressBar, actionLayout);

        categoryName = "action";

        loadCategory();
    }

    @Override
    protected ProductAdaptor getAdaptor() {
        ProductAdaptor itemsAdapter = new ProductAdaptor(this, R.layout.action_game_list_view_item,
                getProductList(), categoryName);
        return itemsAdapter;
    }
}