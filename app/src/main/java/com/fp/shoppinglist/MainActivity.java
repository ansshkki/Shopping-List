package com.fp.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemsListAdapter adapter;
    List<Item> newItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ItemsListAdapter(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new SwipeToDeleteCallback(this, adapter);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);

        adapter.organizeList();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newItems.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                    newItems.add(ds.getValue(Item.class));
                adapter.addItemsToAdapter(newItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            Intent i = new Intent(this, AddItemsActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.sign_out) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private final ItemsListAdapter adapter;
        private final Drawable icon;
        private final ColorDrawable background;

        public SwipeToDeleteCallback(Context context, ItemsListAdapter adapter) {
            super(0, ItemTouchHelper.LEFT);
            this.adapter = adapter;
            icon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
            background = new ColorDrawable(Color.RED);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.removeItemFromAdapter(viewHolder.getAdapterPosition());

        }

        // Prevent swiping for ShopsViewHolder.
        @Override
        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            if (viewHolder instanceof ItemsListAdapter.ShopsViewHolder) return 0;
            return super.getSwipeDirs(recyclerView, viewHolder);
        }

        // Draw red background with delete icon .
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            View itemView = viewHolder.itemView;

            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + icon.getIntrinsicHeight();

            if (dX < 0) { // Swiping to the left
                int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else if (dX == 0) { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }

            background.draw(c);
            icon.draw(c);
        }
    }
}