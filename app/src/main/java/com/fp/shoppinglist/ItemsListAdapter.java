package com.fp.shoppinglist;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ItemsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainActivity activity;
    List<Item> items = new ArrayList<>();
    List<String> shops = new ArrayList<>();
    Item recentlyDeletedItem;
    int recentlyDeletedItemPosition;


    public ItemsListAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public void addItemsToAdapter(List<Item> list) {
        items.clear();
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

        ArrayList<Item> temp = new ArrayList<>(items);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            temp.removeIf(item -> item.getQuantity().equals("0"));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getUid());
        myRef.setValue(temp, (error, ref) -> {
            if (error != null) {
                undoDelete();
                Snackbar.make(activity.findViewById(R.id.coordinator_layout), error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }


    private void showUndoSnackbar() {
        View view = activity.findViewById(R.id.coordinator_layout);
        Snackbar snackbar = Snackbar.make(view, "Item removed", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> undoDelete());
        snackbar.show();
    }


    private void undoDelete() {
        if (!shops.contains(recentlyDeletedItem.getShopName()))
            items.add(new Item("", recentlyDeletedItem.getShopName(), "0", "not taken", R.drawable.blank30, ""));
        items.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);

        ArrayList<Item> temp = new ArrayList<>(items);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            temp.removeIf(item -> item.getQuantity().equals("0"));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getUid());
        myRef.setValue(temp);
    }

    public void organizeList() {
        //get shops
        shops.clear();

        for (int i = 0; i < items.size(); i++) {
            if (!shops.contains(items.get(i).getShopName()))
                shops.add(items.get(i).getShopName());
        }

        for (int i = 0; i < shops.size(); i++) {
            items.add(new Item("", shops.get(i), "0", "not taken", R.drawable.blank30, ""));
        }

        //list'sequence correction
        List<Item> temp = new ArrayList<>();

        for (int j = 0; j < shops.size(); j++) {
            String shopName = shops.get(j);
            temp.add(new Item("", shops.get(j), "0", "not taken", R.drawable.blank30, ""));

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
                return new ItemsViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_row, parent, false);
                return new ShopsViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item current = items.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                ItemsViewHolder viewHolder0 = (ItemsViewHolder) holder;
                Context context = viewHolder0.status.getContext();

                String name = context.getSharedPreferences("MyData", MODE_PRIVATE).getString("personName", "GUEST");
                int personPhotoName = context.getSharedPreferences("MyData", MODE_PRIVATE).getInt("personPhotoName", 0);

                viewHolder0.item_name.setText(current.getName());
                viewHolder0.details.setText(current.getDetails());
                viewHolder0.quantity.setText(current.getQuantity());
                viewHolder0.status.setImageResource(current.getStatusPhoto());
                viewHolder0.status.setOnClickListener(view -> {
                    if (!(current.getStatusPhoto() == R.drawable.blank30)) {
                        if (current.getStatus().equals(name)) {
                            viewHolder0.status.setImageResource(R.drawable.blank30);
                            current.setStatusPhoto(R.drawable.blank30);
                            current.setStatus("not taken");
                        } else {
                            Toast.makeText(context.getApplicationContext(), "taken by " + current.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        viewHolder0.status.setImageResource(personPhotoName);
                        current.setStatus(name);
                        current.setStatusPhoto(personPhotoName);
                    }
                    notifyDataSetChanged();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getUid());
                    myRef.setValue(items);
                });
                break;
            case 1:
                ShopsViewHolder viewHolder1 = (ShopsViewHolder) holder;
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

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, details, quantity;
        ImageView status;

        public ItemsViewHolder(@NonNull View view) {
            super(view);
            item_name = view.findViewById(R.id.item_name_text_view);
            details = view.findViewById(R.id.details_text_view);
            quantity = view.findViewById(R.id.quantity_text_view);
            status = view.findViewById(R.id.status_image_view);
        }
    }

    public static class ShopsViewHolder extends RecyclerView.ViewHolder {
        TextView shop;

        public ShopsViewHolder(@NonNull View view) {
            super(view);
            shop = view.findViewById(R.id.shop_text_view);

        }
    }

}

