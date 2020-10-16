package com.fp.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Item> items = new ArrayList<>();
    List<String> shops = new ArrayList<>();

    ShoppingAdapter() {
        items.add(new Item("Potato", "laham", "1.5 kg", "not token", "no details"));
        items.add(new Item("Potato", "Vegetative", "1.5 kg", "not token", "no details"));
        items.add(new Item("pencil", "library", "1", "not token", "maped "));
        getShops();
        sequenceList();

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

        return items.get(position).getName().equals("") ? 1 : 0;
    }

    public void getShops() {

        for (int i = 0; i < items.size(); i++) {
            if (!shops.contains(items.get(i).getShopName()))
                shops.add(items.get(i).getShopName());
        }

        for (int i = 0; i < shops.size(); i++) {
            items.add(new Item("", shops.get(i), "", "", ""));
        }
    }


    private class itemsViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, details, quantity, status;

        public itemsViewHolder(@NonNull View view) {
            super(view);
            item_name = view.findViewById(R.id.item_name_text_view);
            details = view.findViewById(R.id.details_text_view);
            quantity = view.findViewById(R.id.quantity_text_view);
            status = view.findViewById(R.id.status_text_view);
        }
    }

    private class shopsViewHolder extends RecyclerView.ViewHolder {

        TextView shop;

        public shopsViewHolder(@NonNull View view) {
            super(view);
            shop = view.findViewById(R.id.shop_text_view);

        }
    }



    private void sequenceList (){
        List<Item> temp = new ArrayList<>();

        for(int j = 0; j < shops.size() ; j++) {
            String shopName = shops.get(j);
            temp.add(new Item("", shops.get(j), "", "", ""));

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getShopName().equals(shopName) && !items.get(i).getName().equals(""))
                    temp.add(items.get(i));
            }
        }

        items = temp ;


    }

}

