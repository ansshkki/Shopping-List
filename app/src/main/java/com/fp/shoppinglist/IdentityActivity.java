package com.fp.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class IdentityActivity extends AppCompatActivity {

    EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);

        nameEditText = findViewById(R.id.personName_ET);

    }

    public void goToMain(View view) {

        if(nameEditText.getText().toString().isEmpty()){

            nameEditText.setError("Name is required");

        }else {

            SharedPreferences sp = getSharedPreferences("MyData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("personName", nameEditText.getText().toString());
            editor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);

        }
    }
}