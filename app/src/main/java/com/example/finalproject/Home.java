package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Globals globals = new Globals();
        globals.transStatus(getWindow());

    }

    private void setupRecyclerView() {
        RecyclerView content = (RecyclerView) findViewById(R.id.contentView);
        content.setLayoutManager(new LinearLayoutManager(this));
        //content.setAdapter(HotelAdapter(hotelList));


    }

}