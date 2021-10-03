package com.example.gamesearch.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gamesearch.R;
import com.example.gamesearch.interfaces.ListActivity;

public class ActionListActivity extends AppCompatActivity implements ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);
    }

    @Override
    public void loadCategory() {

    }

    @Override
    public void goToNextCategory() {

    }

    @Override
    public void goToPreviousCategory() {

    }
}