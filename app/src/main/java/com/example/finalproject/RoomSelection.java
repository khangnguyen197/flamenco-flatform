package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RoomSelection extends AppCompatActivity {

    private RecyclerView content;
    private TextView roomTotal;
    private Button btnReserve;
    private ImageButton btnBack;

    private List<Room> roomList = new ArrayList<>();;
    private RoomAdapter roomAdapter;

    private FirebaseFirestore fs;

    private static final String DATABASE_ROOT_COLLECTION = "hotel_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_selection);
        Globals globals = new Globals();
        globals.transStatus(getWindow());

        roomTotal = findViewById(R.id.total_price);
        btnReserve = findViewById(R.id.reserve_button);

        btnBack = findViewById(R.id.imageButton);
        content = (RecyclerView) findViewById(R.id.room_recycler);

        fs = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        final String hotelID = intent.getStringExtra("hotelID");
        final String isAdmin = intent.getStringExtra("isAdmin");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HotelDetail.class);
                intent.putExtra("hotelID", hotelID);
                intent.putExtra("isAdmin",isAdmin);
                startActivity(intent);
                finish();
            }
        });

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
            }
        });

        setupRecyclerView(hotelID);
    }

    private void setupRecyclerView(final String hotelID){
        fs.collection(DATABASE_ROOT_COLLECTION).document(hotelID).collection("roomType").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Room roomInfo = new Room();

                                roomInfo.name = document.getId().toUpperCase();
                                roomInfo.description = document.getString("description");
                                roomInfo.price = document.getString("price");
                                roomInfo.desline1 = document.getString("desline1");
                                roomInfo.desline2 = document.getString("desline2");
                                roomInfo.desline3 = document.getString("desline3");

                                roomList.add(roomInfo);
                            }
                            roomAdapter = new RoomAdapter(RoomSelection.this, roomList, roomTotal);
                            LinearLayoutManager LLM = new LinearLayoutManager(RoomSelection.this);
                            content.setLayoutManager(LLM);
                            content.setAdapter(roomAdapter);
                        }
                    }
                });
    }
}