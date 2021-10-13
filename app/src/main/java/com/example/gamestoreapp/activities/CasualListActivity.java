package com.example.gamestoreapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamestoreapp.R;

public class CasualListActivity extends CategoryListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ProgressBar progressBar = findViewById(R.id.load_progressbar);
        ListView listView = findViewById(R.id.list_view);
        LinearLayout actionLayout = findViewById(R.id.list_layout);
        ImageView categoryImageView = findViewById(R.id.header_image);
        TextView nextImageIcon = findViewById(R.id.next_image_icon);
        TextView prevImageIcon = findViewById(R.id.prev_image_icon);

        TextView title = findViewById(R.id.category_title);
        title.setText("Casual");

        vh = new ViewHolder(listView, progressBar, actionLayout,
                categoryImageView, nextImageIcon, prevImageIcon);

        categoryName = "casual";

        loadCategory();
    }
}