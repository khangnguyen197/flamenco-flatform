package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RoomSelection extends AppCompatActivity {

    private RecyclerView content;
    private TextView roomTotal;
    private Button btnReserve;
    private EditText edt_date;
    private ImageButton btnBack;
    private TextView[] roomType;

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

        roomTotal = findViewById(R.
                id.total_price);
        btnReserve = findViewById(R.id.reserve_button);
        btnBack = findViewById(R.id.imageButton);
        edt_date = findViewById(R.id.ed_date);
        content = (RecyclerView) findViewById(R.id.room_recycler);

       roomType = new TextView[5];
       for(int i=0; i<roomType.length; i++){
           roomType[i] = new TextView(this);
           roomType[i].setText("");
       }

        fs = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        final String hotelID = intent.getStringExtra("hotelID");
        final String isAdmin = intent.getStringExtra("isAdmin");

        Calendar calendar;
        calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(RoomSelection.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int day) {
                       month =month+1;
                       String date = day+"/"+month+"/"+year;
                       edt_date.setText(date);
                   }
               },year,month,day);
               datePickerDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HotelDetail.class);
                intent.putExtra("hotelID", hotelID);
                intent.putExtra("isAdmin", isAdmin);
                startActivity(intent);
                finish();
            }
        });

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HotelReservationActivity.class);
                intent.putExtra("hotelID", hotelID);
                intent.putExtra("isAdmin", isAdmin);
                intent.putExtra("priceTotal", roomTotal.getText().toString());
                intent.putExtra("length", roomType.length);
                intent.putExtra("date",edt_date.getText().toString());

                Log.e("ERROR 111",": "+edt_date.getText().toString());

                for(int i = 0; i <roomType.length; i++){
                    intent.putExtra("roomType"+i, roomType[i].getText().toString());
                }

                startActivity(intent);
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
                            roomAdapter = new RoomAdapter(RoomSelection.this, roomList, roomTotal, roomType);
                            LinearLayoutManager LLM = new LinearLayoutManager(RoomSelection.this);
                            content.setLayoutManager(LLM);
                            content.setAdapter(roomAdapter);
                        }
                    }
                });
    }
}