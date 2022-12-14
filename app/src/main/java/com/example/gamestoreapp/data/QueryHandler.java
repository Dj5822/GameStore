package com.example.gamestoreapp.data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gamestoreapp.model.Game;
import com.example.gamestoreapp.model.GameProduct;
import com.example.gamestoreapp.model.Item;
import com.example.gamestoreapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * @return List of products that will be filled
     */
    public static List<Product> queryCategoryCollection(String categoryName,
                                                        QueryListener queryListener,
                                                        AppCompatActivity activity) {
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
                                    queryProductCollection(productSnapshots, productList, activity);
                                } else {
                                    Toast.makeText(activity.getBaseContext(),
                                            "Failed to get products from category",
                                            Toast.LENGTH_LONG).show();
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
    public static List<String> fetchCategoryImageNames(String categoryName, QueryListener queryListener, AppCompatActivity activity) {
        List<String> imageNames = new ArrayList<String>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").
                document(categoryName).get().
                addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<String> result = (List<String>) task.getResult().get("imageNames");
                                    imageNames.addAll(result);
                                    queryListener.OnQueryComplete();
                                } else {
                                    Toast.makeText(activity.getBaseContext(), "Failed to get Category Images!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
        return imageNames;
    }

    /**
     * Queries the firebase database for the top 10 products in the field
     * @param queryListener functional query listener interface
     * @param field
     * @return List of products that will be filled
     */
    public static List<Product> queryField(String field, QueryListener queryListener, AppCompatActivity activity) {
        QueryList<Product> productList = new QueryList<>(queryListener);
        // Get categories collection from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("GameProducts").orderBy(field, Query.Direction.DESCENDING).limit(10).get().
                addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<DocumentSnapshot> productSnapshots = task.getResult().getDocuments();
                                    queryProductCollection(productSnapshots, productList, activity);
                                } else {
                                    Toast.makeText(activity.getBaseContext(), "Failed to load GameProducts collection", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

        return productList;
    }

    /**
     * Queries the database for products whose names or studio names match the given string.
     */
    public static List<Product> searchQuery(String searchInput, QueryListener queryListener, AppCompatActivity activity) {
        QueryList<Product> productList = new QueryList<>(queryListener);
        // Get categories collection from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("GameProducts").whereArrayContains("keywords", searchInput.toLowerCase()).get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<DocumentSnapshot> productSnapshots = task.getResult().getDocuments();
                                    if (productSnapshots.size() == 0) {
                                        productList.complete();
                                    } else {
                                        queryProductCollection(productSnapshots, productList, activity);
                                    }
                                } else {
                                    Toast.makeText(activity.getBaseContext(), "Failed keyword search query!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

        return productList;
    }

    /**
     * Query Helper Method.
     * Takes a list of document snapshots that represent products in the database.
     * The productList will be populated asynchronously with suitable products as the queries return.
     * @param productDocumentSnapshots list of documents representing products
     * @param productList address of product list to populate
     */
    private static void queryProductCollection(List<DocumentSnapshot> productDocumentSnapshots,
                                               QueryList<Product> productList,
                                               AppCompatActivity activity) {

        productList.setExpectedQuerySize(productDocumentSnapshots.size());

        for (int i = 0; i < productDocumentSnapshots.size(); i++) {
            DocumentSnapshot snapshot = productDocumentSnapshots.get(i);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            int pos = i;
            db.collection("GameProducts").document(snapshot.getId()).get()
                    .addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        queryItemCollection(task.getResult(), productList, pos, activity);
                                    } else {
                                        Toast.makeText(activity.getBaseContext(),
                                                "Failed to load products!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
        }
    }

    /**
     * Update viewCount and amountSold on db for the given product
     * @param product product whose fields will be updated
     */
    public static void updateSalesData(Product product) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reference = db.collection("GameProducts").document(String.valueOf(product.getID()));
        reference.update("viewCount", product.getViewCount());
        reference.update("amountSold", product.getAmountSold());
    }

    /**
     * Query Helper Method.
     * Takes a document snapshot that represents a product in the database.
     * The productList will be populated asynchronously with suitable products as the queries return.
     * @param productDocumentSnapshot list of documents representing products
     * @param productList address of product list to populate
     * @param pos position to insert product in list
     */
    private static void queryItemCollection(DocumentSnapshot productDocumentSnapshot,
                                            QueryList<Product> productList,
                                            int pos, AppCompatActivity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = productDocumentSnapshot.get("id", String.class);
        db.collection("games").document(id)
                .get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Product product = createProductFromSnapshots(task.getResult(), productDocumentSnapshot);
                            productList.addWrapper(pos, product);
                        } else {
                            Toast.makeText(activity.getBaseContext(),
                                    "Failed to load Items!", Toast.LENGTH_LONG).show();
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

        Item item = new Game(
                itemSnapshot.getString("name"),
                itemSnapshot.get("description", String.class),
                (List<String>) itemSnapshot.get("imageNames"),
                itemSnapshot.get("iconImageName", String.class),
                itemSnapshot.get("studioName", String.class),
                itemSnapshot.get("ageRestriction", int.class));
        Product product = new GameProduct(
                Integer.parseInt(id),
                item,
                productSnapshot.get("price", int.class),
                productSnapshot.get("amountSold", int.class),
                productSnapshot.get("viewCount", int.class),
                productSnapshot.getBoolean("isMobile"));

        Log.i("Parsing Products", product.getItem().getName() + " loaded.");

        return product;
    }

    /**
     * One-Time use. Populates keyword field in database with substrings to enable searching.
     * Not called anywhere in code currently as it only needs to be used once when a game is
     * added to the database.
     */
    public static void updateKeyWords(AppCompatActivity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("GameProducts").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                String id = documentSnapshot.get("id", String.class);
                                db.collection("games").document(id)
                                        .get().addOnCompleteListener(
                                        new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    List<String> keyWords = getKeyWords(task.getResult());
                                                    documentSnapshot.getReference().update("keywords", keyWords);
                                                } else {
                                                    Toast.makeText(activity.getBaseContext(),
                                                            "Failed to load items for keyword creation!",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                );
                            }
                        } else {
                            Toast.makeText(activity.getBaseContext(),
                                    "Failed to load Products for keyword creation!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    /**
     * Gets substrings of the name and studio for a particular game snapshot.
     * @param gameSnapshot snapshot to get keywords for
     * @return list of keywords
     */
    private static List<String> getKeyWords(DocumentSnapshot gameSnapshot) {
        Set<String> keywords = new HashSet<String>();
        String studioName = gameSnapshot.getString("studioName");
        String name = gameSnapshot.getString("name");

        keywords.addAll(getSubstrings(name));
        keywords.addAll(getSubstrings(studioName));

        return new ArrayList<>(keywords);
    }

    /**
     * Returns a list of substrings for the input string. Used to create keywords.
     * @param name string to get substrings of
     * @return list of substrings
     */
    private static List<String> getSubstrings(String name) {
        name = name.toLowerCase();
        int length = name.length();
        List<String> keywords = new ArrayList<String>((length * (length-1))/2);
        String currentString = "";
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j <= length; j++) {
                keywords.add(name.substring(i, j));
            }
        }
        return keywords;
    }

}
