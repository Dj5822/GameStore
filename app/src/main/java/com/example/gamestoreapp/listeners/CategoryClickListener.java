package com.example.gamestoreapp.listeners;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamestoreapp.activities.ActionListActivity;

/**
 * Listener that opens a category class when clicked
 */
public class CategoryClickListener implements View.OnClickListener {

    Class<?> activity;

    public CategoryClickListener(Class<?> activity) {
        super();
        this.activity = activity;
    }

    @Override
    /**
     * Open the relevant category
     */
    public void onClick(View view) {
        Intent categoryOpenIntent = new Intent(view.getContext(), activity);
        categoryOpenIntent.putExtra("MessageFromMainActivity", "This message came from Main Activity");
        view.getContext().startActivity(categoryOpenIntent);
    }
}
