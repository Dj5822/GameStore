package com.example.gamesearch.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gamesearch.R;
import com.example.gamesearch.interfaces.Store;
import com.example.gamesearch.listeners.CategoryClickListener;

public class MainActivity extends AppCompatActivity {

    private Store store;
    private CategoryClickListener listener;

    void startSearch(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}