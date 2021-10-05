package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.ListActivity;

public class StrategyListActivity extends AppCompatActivity implements ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_list);
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