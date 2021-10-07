package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.ListActivity;

public class ActionListActivity extends CategoryListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        ProgressBar progressBar = findViewById(R.id.load_progressbar_action);
        ListView listView = findViewById(R.id.list_view_action);
        vh = new ViewHolder(listView, progressBar);

        categoryName = "action";

        loadCategory();
    }
}