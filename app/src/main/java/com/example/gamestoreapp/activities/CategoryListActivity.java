package com.example.gamestoreapp.activities;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.adaptors.ProductAdaptor;
import com.example.gamestoreapp.implementation.Game;
import com.example.gamestoreapp.implementation.GameProduct;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.ListActivity;
import com.example.gamestoreapp.interfaces.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public abstract class CategoryListActivity  extends AppCompatActivity implements ListActivity {

    protected String categoryName;
    protected ViewHolder vh;
    private List<Product> productList;

    protected class ViewHolder {
        ListView listView;
        ProgressBar progressBar;

        public ViewHolder() {
            listView = findViewById(R.id.list_view);
            progressBar = findViewById(R.id.load_progressbar);
        }
    }

    @Override
    public void loadCategory() {
        fetchCategoryData();
    }

    @Override
    public void goToNextCategory() {

    }

    @Override
    public void goToPreviousCategory() {

    }

    private void fetchCategoryData() {
        // Get categories collection from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").
                document(categoryName).collection("gameProducts").get().
                addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    fetchProductData(task.getResult().getDocuments());
                                    propagateAdapter();
                                } else {
                                    Toast.makeText(getBaseContext(), "Loading Categories Collection failed from Firestore!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
    }

    private void fetchProductData(List<DocumentSnapshot> documentSnapshots) {
        for (DocumentSnapshot snapshot : documentSnapshots) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("GameProducts").document(snapshot.getId()).get()
                    .addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        fetchItemData(task.getResult());
                                    } else {
                                        Toast.makeText(getBaseContext(), "Loading Products Collection failed from Firestore!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
        }
    }

    private void fetchItemData(DocumentSnapshot productSnapshot) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = productSnapshot.get("id", String.class);
        db.collection("games").document(id)
                .get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot itemSnapshot = task.getResult();
                            Item item = new Game(Integer.parseInt(id),
                                    itemSnapshot.get("name", String.class),
                                    itemSnapshot.get("description", String.class),
                                    null, // TODO: images
                                    itemSnapshot.get("iconImageName", String.class),
                                    itemSnapshot.get("studioName", String.class));

                            Product product = new GameProduct(item,
                                    productSnapshot.get("price", int.class),
                                    productSnapshot.get("amountSold", int.class),
                                    productSnapshot.get("viewCount", int.class));

                            productList.add(product);

                            Log.i("Parsing Products", product.getItem().getName() + " loaded.");

                        } else {
                            Toast.makeText(getBaseContext(), "Loading Games Collection failed from Firestore!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void propagateAdapter() {

        if (productList.size() > 0) {
            Log.i("Getting Products", "Success");
            vh.progressBar.setVisibility(View.GONE);
        } else {
            Toast.makeText(getBaseContext(), "Products Collection was empty!", Toast.LENGTH_LONG).show();
        }

        ProductAdaptor itemsAdapter = new ProductAdaptor(this, R.layout.game_list_view_item,
                productList);
        vh.listView.setAdapter(itemsAdapter);
        vh.listView.setVisibility(View.VISIBLE);
    }
}
