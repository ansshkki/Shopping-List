package com.fp.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewItemsAdapter extends RecyclerView.Adapter<NewItemsAdapter.newItemsViewHolder> {

    List<Item> newItems = new ArrayList<>();

    public void addItemsToAdapter(String name, String shopName, String quantity, String details) {
        if (details.equals("")) {
            details = "no details";
        }

        Item item = new Item(name, shopName, quantity, "not taken", R.drawable.blank30, details);
        newItems.add(item);

        notifyDataSetChanged();
    }

    public List<Item> getNewItems() {
        return newItems;
    }

    @NonNull
    @Override
    public newItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item_row, parent, false);
        return new newItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newItemsViewHolder holder, int position) {
        Item current = newItems.get(position);
        holder.details.setText(current.getDetails());
        holder.item_name.setText(current.getName());
        holder.quantity.setText(current.getQuantity());
        holder.imageView.setOnClickListener(view -> {
            newItems.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return newItems.size();
    }

    public static class newItemsViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, details, quantity;
        ImageView imageView;

        public newItemsViewHolder(@NonNull View view) {
            super(view);
            item_name = view.findViewById(R.id.new_item_name_text_view);
            details = view.findViewById(R.id.new_details_text_view);
            quantity = view.findViewById(R.id.new_quantity_text_view);
            imageView = view.findViewById(R.id.delete);
        }
    }


}
