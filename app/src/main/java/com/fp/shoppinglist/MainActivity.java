package com.fp.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    itemsListAdapter adapter ;
    List<Item> newItems  = new ArrayList<>();



    private void readListFromSP(String mainKey , List<Item> tempList){


        tempList.clear();

        SharedPreferences sp = getSharedPreferences(mainKey, MODE_PRIVATE);

        int size = sp.getInt(mainKey+"ListSize" , 0);
        for (int i = 0 ; i < size ; i++) {

            Item temp = new Item(sp.getString(mainKey+"Name"+i ,"ERROR")
                            ,sp.getString(mainKey+"ShopName"+i ,"ERROR")
                            ,sp.getString(mainKey+"Quantity"+i+i ,"ERROR")
                            ,"not token"
                            , sp.getString(mainKey+"Details"+i,""));


            tempList.add(temp);
        }



    }





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new itemsListAdapter();
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


    }


    @Override
    protected void onResume() {
        super.onResume();


        readListFromSP("newItems" ,newItems);
        adapter.AddItemsToAdapter(newItems);

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.add_item){
            Intent i  = new Intent(this , addItems.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}
