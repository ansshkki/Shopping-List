package com.fp.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class addItems extends AppCompatActivity  {

    EditText itemNameNew_ET , shopNameNew_ET ,quantityNew_ET,detailsNew_ET ;
    String itemNameNew , shopNameNew ,quantityNew,detailsNew ;
    newItemsAdapter adapter ;
    RecyclerView recyclerView ;



    public void saveList (List<Item> list , String mainKey){
        Intent intent = new Intent(this ,MainActivity.class);
        intent.putExtra(mainKey+"ListSize",list.size()) ;

        for(int i = 0 ; i < list.size() ; i++){
            Item temp = list.get(i) ;
            intent.putExtra(mainKey+"Name"+i ,temp.getName()) ;
            intent.putExtra(mainKey+"ShopName"+i ,temp.getShopName()) ;
            intent.putExtra(mainKey+"Quantity"+i ,temp.getQuantity()) ;
            intent.putExtra(mainKey+"Details"+i ,temp.getDetails()) ;
        }


        startActivity(intent);


    }




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

        List<Item> list = adapter.getNewItems();
        Intent i = new Intent(this, MainActivity.class);


        saveList(list,"newItems");

        return super.onOptionsItemSelected(item);
    }
}