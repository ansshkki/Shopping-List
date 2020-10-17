package com.fp.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class addItems extends AppCompatActivity {

    EditText itemNameNew_ET , shopNameNew_ET ,quantityNew_ET,detailsNew_ET ;
    String itemNameNew , shopNameNew ,quantityNew,detailsNew ;
    newItemsAdapter adapter ;
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        itemNameNew_ET = findViewById(R.id.itemNameNew_ET);
        shopNameNew_ET = findViewById(R.id.shopNameNew_ET);
        quantityNew_ET = findViewById(R.id.quantityNew_ET);
        detailsNew_ET = findViewById(R.id.detailsNew_ET);

        adapter = new newItemsAdapter() ;
        recyclerView = findViewById(R.id.new_items_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    public void addToNew(View view) {

        itemNameNew =  itemNameNew_ET.getText().toString();
        shopNameNew = shopNameNew_ET.getText().toString();
        quantityNew = quantityNew_ET.getText().toString();
        detailsNew = detailsNew_ET.getText().toString();

        adapter.addItemsToAdapter(itemNameNew , shopNameNew ,quantityNew ,detailsNew);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.add_items_activity_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }



    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.Done){
            Intent i = new Intent(this , MainActivity.class);
            i.putExtra("newItems" , (ArrayList)adapter.getNewItems());
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}