package com.example.gamestoreapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import com.example.gamestoreapp.R;
import com.example.gamestoreapp.interfaces.ListActivity;
import com.example.gamestoreapp.interfaces.Store;
import com.example.gamestoreapp.listeners.CategoryClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Store store;
    private CategoryClickListener listener;

    private class ViewHolder {
        CardView actionCardView, strategyCardView, casualCardView, simulationCardView;
        public ViewHolder() {
            actionCardView = findViewById(R.id.card_view_action);
            strategyCardView = findViewById(R.id.card_view_strategy);
            casualCardView = findViewById(R.id.card_view_casual);
            simulationCardView = findViewById(R.id.card_view_simulation);
        }
    }

    void startSearch(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewHolder vh = new ViewHolder();

        vh.actionCardView.setOnClickListener(new CategoryClickListener(ActionListActivity.class));
    }
}