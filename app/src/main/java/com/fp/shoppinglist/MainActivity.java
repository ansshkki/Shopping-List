package com.fp.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

//import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    itemsListAdapter adapter;
    List<Item> newItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new itemsListAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.organizeList();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        setSupportActionBar(toolbar);
    }

    private void readList(String mainKey, List<Item> tempList) {

        tempList.clear();

        for (int i = 0, size = getIntent().getIntExtra(mainKey + "ListSize", 0); i < size; i++) {

            Item temp = new Item(getIntent().getStringExtra(mainKey + "Name" + i),
                    getIntent().getStringExtra(mainKey + "ShopName" + i),
                    getIntent().getStringExtra(mainKey + "Quantity" + i),
                    "not taken",
                    getIntent().getStringExtra(mainKey + "Details" + i));

            tempList.add(temp);
        }
    }

    protected void onResume() {
        super.onResume();
        adapter.organizeList();
        readList("newItems", newItems);
        adapter.AddItemsToAdapter(newItems);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            Intent i = new Intent(this, addItems.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.sign_out) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
