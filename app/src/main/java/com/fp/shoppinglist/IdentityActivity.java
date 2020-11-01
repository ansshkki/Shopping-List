package com.fp.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IdentityActivity extends AppCompatActivity {

    EditText nameEditText;
    int personPhotoName = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);

        nameEditText = findViewById(R.id.personName_ET);

    }

    public void goToMain(View view) {

        if (nameEditText.getText().toString().isEmpty()) {
            nameEditText.setError("Name is required");
        } else if (personPhotoName == 0) {
            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sp = getSharedPreferences("MyData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("personName", nameEditText.getText().toString());
            editor.putInt("personPhotoName", personPhotoName);

            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    public void imageClicked(View view) {
        int id = view.getId();
        if (id == R.id.father) {
            personPhotoName = R.drawable.father;
        } else if (id == R.id.son) {
            personPhotoName = R.drawable.son;
        } else if (id == R.id.daughter) {
            personPhotoName = R.drawable.daughter;
        } else if (id == R.id.mother) {
            personPhotoName = R.drawable.mother;
        }
    }
}
