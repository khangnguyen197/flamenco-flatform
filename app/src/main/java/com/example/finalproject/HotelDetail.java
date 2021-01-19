package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HotelDetail extends AppCompatActivity {

    TextView tx;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

//        tx = findViewById(R.id.name);
//        img = findViewById(R.id.image_detail);
//
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
//        String url  = intent.getStringExtra("url");
//
//        tx.setText(name);
//
//        Picasso.with(this).load(url)
//            .fit()
//            .centerCrop()
//            .into(img);

    }
}