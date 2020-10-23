package com.fp.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ItemsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainActivity activity;
    List<Item> items = new ArrayList<>();
    List<String> shops = new ArrayList<>();
    Item recentlyDeletedItem;
    int recentlyDeletedItemPosition;

    public void AddItemsToAdapter(MainActivity activity, List<Item> list) {
        this.activity = activity;
        items.addAll(list);
        notifyDataSetChanged();

        organizeList();
    }

    public void removeItemFromAdapter(int position) {
        recentlyDeletedItem = items.get(position);
        recentlyDeletedItemPosition = position;
        items.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();

        organizeList();
    }

    private void showUndoSnackbar() {
        View view = activity.findViewById(R.id.coordinator_layout);
        Snackbar snackbar = Snackbar.make(view, "Item removed", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        items.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }

    public void organizeList() {
        //get shops
        for (int i = 0; i < items.size(); i++) {
            if (!shops.contains(items.get(i).getShopName()))
                shops.add(items.get(i).getShopName());
        }

        for (int i = 0; i < shops.size(); i++) {
            items.add(new Item("", shops.get(i), "0", "", ""));
        }

        //list'sequence correction
        List<Item> temp = new ArrayList<>();

        for (int j = 0; j < shops.size(); j++) {
            String shopName = shops.get(j);
            temp.add(new Item("", shops.get(j), "0", "", ""));

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getShopName().equals(shopName) && !items.get(i).getQuantity().equals("0"))
                    temp.add(items.get(i));
            }
        }

        items = temp;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {

            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
                return new itemsViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_row, parent, false);
                return new shopsViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item current = items.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                itemsViewHolder viewHolder0 = (itemsViewHolder) holder;
                viewHolder0.item_name.setText(current.getName());
                viewHolder0.details.setText(current.getDetails());
                viewHolder0.quantity.setText(current.getQuantity());
                viewHolder0.status.setText(current.getStatus());
                break;

            case 1:
                shopsViewHolder viewHolder1 = (shopsViewHolder) holder;
                viewHolder1.shop.setText(current.getShopName());

                break;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {

        return items.get(position).getQuantity().equals("0") ? 1 : 0;
    }

    private static class itemsViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, details, quantity, status;

        public itemsViewHolder(@NonNull View view) {
            super(view);
            item_name = view.findViewById(R.id.item_name_text_view);
            details = view.findViewById(R.id.details_text_view);
            quantity = view.findViewById(R.id.quantity_text_view);
            status = view.findViewById(R.id.status_text_view);
        }
    }

    private static class shopsViewHolder extends RecyclerView.ViewHolder {

        TextView shop;

        public shopsViewHolder(@NonNull View view) {
            super(view);
            shop = view.findViewById(R.id.shop_text_view);

        }
    }

}

