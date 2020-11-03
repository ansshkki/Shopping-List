package com.fp.shoppinglist;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IdentityActivity extends AppCompatActivity {

    EditText nameEditText;
    int personPhotoName = 0;
    View previousView;

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
        if (previousView != null) {
            ObjectAnimator upAnimator = ObjectAnimator.ofFloat(previousView, "translationZ", 0);
            upAnimator.setDuration(200);
            upAnimator.setInterpolator(new AccelerateInterpolator());
            upAnimator.start();
        }

        ObjectAnimator downAnimator = ObjectAnimator.ofFloat(view, "translationZ", 16);
        downAnimator.setDuration(200);
        downAnimator.setInterpolator(new DecelerateInterpolator());
        downAnimator.start();

        previousView = view;

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
