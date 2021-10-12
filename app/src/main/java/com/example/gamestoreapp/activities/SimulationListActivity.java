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

public class SimulationListActivity extends CategoryListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_list);

        ProgressBar progressBar = findViewById(R.id.load_progressbar_simulation);
        ListView listView = findViewById(R.id.list_view_simulation);
        LinearLayout actionLayout = findViewById(R.id.simulation_layout);
        ImageView categoryImageView = findViewById(R.id.header_image_simulation);
        TextView nextImageIcon = findViewById(R.id.next_image_icon_simulation);
        TextView prevImageIcon = findViewById(R.id.prev_image_icon_simulation);

        vh = new ViewHolder(listView, progressBar, actionLayout,
                categoryImageView, nextImageIcon, prevImageIcon);

        categoryName = "simulation";

        loadCategory();
    }
}