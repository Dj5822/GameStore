package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.Store;
import com.example.gamestoreapp.listeners.CategoryClickListener;

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