package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView content;
    protected List<Hotel> hotelList = new ArrayList<Hotel>();;
    private HotelAdapter hotelAdapter;

    private FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Globals globals = new Globals();
        globals.transStatus(getWindow());

        fs = FirebaseFirestore.getInstance();
        content = (RecyclerView) findViewById(R.id.contentView);

        setupRecyclerView();

    }

    private void setupRecyclerView() {

        fs.collection("hotel_info").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String hotelName = document.getString("name");
                        String numberAdd = document.getString("numberAddress");
                        String district = document.getString("district");
                        String ward = document.getString("ward");
                        String phone = document.getString("phone");
                        String special = document.getString("special");
                        String price = document.getString("priceRange");

                        Hotel hotelInfo = new Hotel(hotelName, numberAdd, district, ward, phone, special,price);
                        hotelList.add(hotelInfo);

                    }
                    hotelAdapter = new HotelAdapter(hotelList);

                    Log.e("Size",": "+hotelAdapter.getItemCount());

                    content.setHasFixedSize(true);
                    LinearLayoutManager LLM = new LinearLayoutManager(Home.this);
                    content.setLayoutManager(LLM);
                    content.setAdapter(hotelAdapter);
                }
            }
        });

    }// setupRecyclerView end

    private void setList(){

    }// setList end

}