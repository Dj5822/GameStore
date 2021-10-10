package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.ListActivity;

public class CasualListActivity extends CategoryListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casual_list);

        ProgressBar progressBar = findViewById(R.id.load_progressbar_casual);
        ListView listView = findViewById(R.id.list_view_casual);
        LinearLayout actionLayout = findViewById(R.id.casual_layout);
        ImageView categoryImageView = findViewById(R.id.header_image_casual);
        TextView nextImageIcon = findViewById(R.id.next_image_icon_casual);
        TextView prevImageIcon = findViewById(R.id.prev_image_icon_casual);

        vh = new ViewHolder(listView, progressBar, actionLayout,
                categoryImageView, nextImageIcon, prevImageIcon);

        categoryName = "casual";

        loadCategory();
    }
}