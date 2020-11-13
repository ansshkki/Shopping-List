package com.fp.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddItemsActivity extends AppCompatActivity {

    EditText itemNameNew_ET, shopNameNew_ET, quantityNew_ET, detailsNew_ET;
    String itemNameNew, shopNameNew, quantityNew, detailsNew;
    NewItemsAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        itemNameNew_ET = findViewById(R.id.itemNameNew_ET);
        shopNameNew_ET = findViewById(R.id.shopNameNew_ET);
        quantityNew_ET = findViewById(R.id.quantityNew_ET);
        detailsNew_ET = findViewById(R.id.detailsNew_ET);

        adapter = new NewItemsAdapter();
        recyclerView = findViewById(R.id.new_items_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void saveList(List<Item> list) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Item> items = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren())
                    items.add(ds.getValue(Item.class));
                items.addAll(list);
                myRef.setValue(items, (error, ref) -> {
                    finish();
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addToNew(View view) {
        boolean correctInput = true;
        EditText[] editTexts = {itemNameNew_ET, shopNameNew_ET, quantityNew_ET};

        itemNameNew = itemNameNew_ET.getText().toString();
        shopNameNew = shopNameNew_ET.getText().toString();
        quantityNew = quantityNew_ET.getText().toString();
        detailsNew = detailsNew_ET.getText().toString();

        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("Required");
                correctInput = false;
            }
        }
        if (!correctInput) {
            Toast.makeText(this, "please fill the fields", Toast.LENGTH_LONG).show();
        }

        if (quantityNew_ET.getText().toString().equals("0")) {
            quantityNew_ET.setError("Quantity can't be 0");
            correctInput = false;
        }

        if (correctInput) {
            adapter.addItemsToAdapter(itemNameNew, shopNameNew, quantityNew, detailsNew);

            itemNameNew_ET.setText("");
            shopNameNew_ET.setText("");
            quantityNew_ET.setText("");
            detailsNew_ET.setText("");
        }

        itemNameNew_ET.requestFocus();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_items_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Done) {
            List<Item> list = adapter.getNewItems();
            saveList(list);
            return true;
        }
        return false;
    }
}