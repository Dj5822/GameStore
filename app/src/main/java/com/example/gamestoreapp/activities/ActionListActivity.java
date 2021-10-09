package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.ListActivity;

public class ActionListActivity extends CategoryListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        ProgressBar progressBar = findViewById(R.id.load_progressbar_action);
        ListView listView = findViewById(R.id.list_view_action);
        LinearLayout actionLayout = findViewById(R.id.action_layout);
        ImageView categoryImageView = findViewById(R.id.header_image_action);
        TextView nextImageIcon = findViewById(R.id.next_image_icon_action);
        TextView prevImageIcon = findViewById(R.id.prev_image_icon_action);

        vh = new ViewHolder(listView, progressBar, actionLayout,
                categoryImageView, nextImageIcon, prevImageIcon);

        categoryName = "action";

        loadCategory();
    }
}