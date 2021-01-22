package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelReservationActivity extends AppCompatActivity {

    private RecyclerView content;
    private List<HotelReservation> reservationList = new ArrayList<>();
    private HotelReservationAdapter reservationAdapter;

    private static final String DATABASE_ROOT_COLLECTION_RESERVATION = "reservation";

    private FirebaseFirestore fs;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservation);

        Globals globals = new Globals();
        globals.transStatus(getWindow());

        fs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        content = (RecyclerView) findViewById(R.id.contentView);

        Intent intent = getIntent();
        final String hotelID = intent.getStringExtra("hotelID");
        final String isAdmin = intent.getStringExtra("isAdmin");
        final String priceTotal = intent.getStringExtra("priceTotal");
        final int length = intent.getIntExtra("length", 0);
        final String date = intent.getStringExtra("date");

        Log.e("ERROR",": "+date);

        setupRecyclerView();
    }

    private void setupRecyclerView(){

        fs.collection(DATABASE_ROOT_COLLECTION_RESERVATION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HotelReservation hr = new HotelReservation();

                                hr.name = document.getString("name");
                                hr.hotelName = document.getString("hotelName");
                                hr.dateTime = document.getString("dateTime");
                                hr.priceTotal = document.getString("priceTotal");
                                hr.roomType = document.getString("roomType");

                                reservationList.add(hr);

                            }
                            reservationAdapter = new HotelReservationAdapter(HotelReservationActivity.this, reservationList);
                            LinearLayoutManager LLM = new LinearLayoutManager(HotelReservationActivity.this);
                            content.setLayoutManager(LLM);
                            content.setAdapter(reservationAdapter);
                        }
                    }
                });


    }

}







