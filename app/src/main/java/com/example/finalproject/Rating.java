package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rating extends AppCompatActivity {
    RatingBar ratingBar;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Globals globals = new Globals();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        globals.transStatus(getWindow());

        /** Rating star func */
        ratingBar = findViewById(R.id.rating_bar);
        submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Rating.this, "Thank you for your rating.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Rating.this, Home.class);
                startActivity(intent);
            }
        });
    }
}