package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RoomSelection extends AppCompatActivity {

    private RecyclerView content;
    private List<Hotel> hotelList = new ArrayList<>();;
    private RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_selection);
        Globals globals = new Globals();
        globals.transStatus(getWindow());
    }

    private void setupRecyclerView(){

    }
}