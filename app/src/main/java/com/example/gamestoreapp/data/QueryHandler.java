package com.example.gamestoreapp.data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gamestoreapp.implementation.Game;
import com.example.gamestoreapp.implementation.GameProduct;
import com.example.gamestoreapp.interfaces.Item;
import com.example.gamestoreapp.interfaces.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper Class for Database Queries
 */
public class QueryHandler {

    /**
     * Functional Interface
     */
    public interface QueryListener {
        void OnQueryComplete();
    }

    /**
     * Queries the firebase database for products in the specified category.
     * The productList will be populated asynchronously as the queries return.
     * @param categoryName id of the category in the database
     * @param queryListener functional query listener interface
     */
    public static List<Product> queryCategoryCollection(String categoryName, QueryListener queryListener) {
        QueryList<Product> productList = new QueryList<>(queryListener);
        // Get categories collection from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").
                document(categoryName).collection("gameProducts").get().
                addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<DocumentSnapshot> productSnapshots = task.getResult().getDocuments();
                                    queryProductCollection(productSnapshots, productList);
                                } else {
                                    throw new RuntimeException("Failed to load categories Collection");
                                }
                            }
                        });

        return productList;
    }

    /**
     * Fetch the image names for a particular category
     * @param categoryName id of the category in the database
     * @return List of image names
     */
    public static List<String> fetchCategoryImageNames(String categoryName, QueryListener queryListener) {
        QueryList<String> imageNames = new QueryList<String>(queryListener);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").
                document(categoryName).get().
                addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<String> result = (List<String>) task.getResult().get("imageNames");
                                    imageNames.setExpectedQuerySize(result.size());
                                    imageNames.addAll((List<String>) task.getResult().get("imageNames"));
                                } else {
                                    throw new RuntimeException("Failed to load categories Collection");
                                }
                            }
                        });
        return imageNames;
    }

    /**
     * Query Helper Method.
     * Takes a list of document snapshots that represent products in the database.
     * The productList will be populated asynchronously with suitable products as the queries return.
     * @param productDocumentSnapshots list of documents representing products
     * @param productList address of product list to populate
     */
    private static void queryProductCollection(List<DocumentSnapshot> productDocumentSnapshots, QueryList<Product> productList) {

        productList.setExpectedQuerySize(productDocumentSnapshots.size());

        for (DocumentSnapshot snapshot : productDocumentSnapshots) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("GameProducts").document(snapshot.getId()).get()
                    .addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        queryItemCollection(task.getResult(), productList);
                                    } else {
                                        throw new RuntimeException("Failed to load Products Collection");
                                    }
                                }
                            }
                    );
        }
    }

    /**
     * Query Helper Method.
     * Takes a document snapshot that represents a product in the database.
     * The productList will be populated asynchronously with suitable products as the queries return.
     * @param productDocumentSnapshot list of documents representing products
     * @param productList address of product list to populate
     */
    private static void queryItemCollection(DocumentSnapshot productDocumentSnapshot, QueryList<Product> productList) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = productDocumentSnapshot.get("id", String.class);
        db.collection("games").document(id)
                .get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Product product = createProductFromSnapshots(task.getResult(), productDocumentSnapshot);
                            productList.add(product);
                        } else {
                            throw new RuntimeException("Failed to load Items collection!");
                        }
                    }
                }
        );
    }

    /**
     * Query Helper Method.
     * Constructs a product from database product and item snapshots.
     * @param itemSnapshot Document snapshot representing an item in the database
     * @param productSnapshot Document snapshot representing a product in the database
     * @return a product constructed from the snapshots
     */
    public static Product createProductFromSnapshots(DocumentSnapshot itemSnapshot, DocumentSnapshot productSnapshot) {
        String id = productSnapshot.get("id", String.class);

        Item item = new Game(Integer.parseInt(id),
                itemSnapshot.getString("name"),
                itemSnapshot.get("description", String.class),
                (List<String>) itemSnapshot.get("imageNames"),
                itemSnapshot.get("iconImageName", String.class),
                itemSnapshot.get("studioName", String.class));
        Product product = new GameProduct(item,
                productSnapshot.get("price", int.class),
                productSnapshot.get("amountSold", int.class),
                productSnapshot.get("viewCount", int.class));
        Log.i("Parsing Products", product.getItem().getName() + " loaded.");

        return product;
    }

}
