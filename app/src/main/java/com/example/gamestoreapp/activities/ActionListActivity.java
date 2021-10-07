package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.ListActivity;

public class ActionListActivity extends CategoryListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        vh = new ViewHolder();
        categoryName = "action";
    }
}