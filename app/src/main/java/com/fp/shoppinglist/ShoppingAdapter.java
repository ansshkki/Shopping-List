package com.fp.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ItemsViewHolder0> {



    List<Item> items = Arrays.asList(new Item("Potato", "Vegetative" ,"1.5 kg" ,"not token" , "no details" ),
    new Item("Potato", "Vegetative" ,"1.5 kg" ,"not token" , "no details" ),
    new Item("Potato", "Vegetative" ,"1.5 kg" ,"not token" , "no details" ));


    @NonNull
    @Override
    public ShoppingAdapter.ItemsViewHolder0 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent , false);
        return new ItemsViewHolder0(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.ItemsViewHolder0 holder, int position) {

        Item current = items.get(position);
        holder.item_name.setText(current.getName());
        holder.details.setText(current.getDetails());
        holder.quantity.setText(current.getQuantity());
        holder.status.setText(current.getStatus());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }




    public  class ItemsViewHolder0 extends RecyclerView.ViewHolder {

        TextView item_name , details ,quantity, status;


        ItemsViewHolder0(View view){
            super(view);
            item_name = view.findViewById(R.id.item_name_text_view);
            details = view.findViewById(R.id.details_text_view);
            quantity = view.findViewById(R.id.quantity_text_view);
            status = view.findViewById(R.id.status_text_view);


        }





    }




}
