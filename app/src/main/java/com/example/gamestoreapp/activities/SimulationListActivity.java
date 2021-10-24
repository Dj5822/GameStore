package com.example.gamestoreapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamestoreapp.R;

public class SimulationListActivity extends CategoryListActivity {

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
        title.setText(R.string.simulation);

        vh = new ViewHolder(listView, progressBar, actionLayout, header);

        categoryName = "simulation";

        loadCategory();
    }
}