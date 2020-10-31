package com.fp.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.annotation.KeepForSdkWithFieldsAndMethods;

public class IdentityActivity extends AppCompatActivity {

    EditText nameEditText;
    String personPhotoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);

        nameEditText = findViewById(R.id.personName_ET);

    }

    public void goToMain(View view) {

        if (nameEditText.getText().toString().isEmpty()) {

            nameEditText.setError("Name is required");

        } else {

            SharedPreferences sp = getSharedPreferences("MyData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("personName", nameEditText.getText().toString());
            editor.putString("personPhotoName", personPhotoName);

            editor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);

        }
    }

    public void imageClicked(View view) {

        switch (view.getId()) {
            case R.id.father:
                personPhotoName = "father";
                break;

            case R.id.son:
                personPhotoName = "son";
                break;
            case R.id.daughter:
                personPhotoName = "daughter";
                break;
            case R.id.mother:
                personPhotoName = "mother";
                break;
        }
    }


}
