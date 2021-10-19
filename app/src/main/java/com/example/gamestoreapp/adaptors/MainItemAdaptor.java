package com.example.gamestoreapp.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamestoreapp.R;
import com.example.gamestoreapp.model.Product;

import java.util.List;

public class MainItemAdaptor extends RecyclerView.Adapter<MainItemAdaptor.ViewHolder> {

    private List<Product> mData;
    private LayoutInflater mInflater;
    private Context context;
    private ItemClickListener mClickListener;

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemNameTextview, itemPriceTextview;
        ImageView itemIcon;

        ViewHolder(View itemView) {
            super(itemView);
            itemNameTextview = itemView.findViewById(R.id.main_list_product_name);
            itemPriceTextview = itemView.findViewById(R.id.main_list_product_price);
            itemIcon = itemView.findViewById(R.id.main_list_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // data is passed into the constructor
    public MainItemAdaptor(Context context, List<Product> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_list_view_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mData.get(position);
        holder.itemNameTextview.setText(product.getItem().getName());
        holder.itemPriceTextview.setText(String.format("%s", product.getCostAsString()));
        holder.itemIcon.setImageResource(context.getResources().getIdentifier(
                product.getItem().getIconImageName(), "drawable",
                context.getPackageName()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // convenience method for getting data at click position
    Product getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
