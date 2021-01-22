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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelModify extends AppCompatActivity {

    private RecyclerView content;
    private TextView roomTotal, hotelName;
    private ImageButton editBtn, btnBack;
    private Button btnSave;

    private List<ModifyRoom> roomList = new ArrayList<>();;
    private ModifyRoomAdapter modifyRoomAdapter;

    private FirebaseFirestore fs;

    private static final String DATABASE_ROOT_COLLECTION = "hotel_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_modify);
        Globals globals = new Globals();
        globals.transStatus(getWindow());

        hotelName = findViewById(R.id.hotel_name);
        roomTotal = findViewById(R.id.total_price);
        editBtn = findViewById(R.id.edit_button);
        btnBack = findViewById(R.id.back_button);
        btnSave = findViewById(R.id.save_button);
        content = (RecyclerView) findViewById(R.id.room_recycler);

        fs = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        final String hotelID = intent.getStringExtra("hotelID");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HotelManageActivity.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HotelManageActivity.class));
            }
        });

        hotelName.setText(hotelID);
        setupRecyclerView(hotelID);
    }

    private void setupRecyclerView(final String hotelID){
        fs.collection(DATABASE_ROOT_COLLECTION).document(hotelID).collection("roomType").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ModifyRoom modifyRoom = new ModifyRoom();
                                modifyRoom.des = document.getString("description");
                                modifyRoom.name = document.getId().toUpperCase();
                                modifyRoom.price = document.getString("price");

                                roomList.add(modifyRoom);
                            }
                            modifyRoomAdapter = new ModifyRoomAdapter(HotelModify.this, roomList);
                            LinearLayoutManager LLM = new LinearLayoutManager(HotelModify.this);
                            content.setLayoutManager(LLM);
                            content.setAdapter(modifyRoomAdapter);
                        }
                    }
                });
    }
}