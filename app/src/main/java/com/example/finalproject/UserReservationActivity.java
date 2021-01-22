package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserReservationActivity extends AppCompatActivity {

    private RecyclerView content;
    private List<UserReservation> reservationList = new ArrayList<>();
    private UserReservationAdapter reservationAdapter;

    private static final String DATABASE_ROOT_COLLECTION_USER = "users";
    private static final String DATABASE_ROOT_COLLECTION_RESERVATION = "reservation";

    private FirebaseFirestore fs;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private String[] roomType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);

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

        roomType = new String[5];

        for(int i = 0; i < length; i++){
            roomType[i] = intent.getStringExtra("roomType"+i);
        }

        setupRecyclerView(hotelID, priceTotal, length, date);
    }

    private void setupRecyclerView(final String hotelID, final String priceTotal, final int length, final String date){

        fs.collection(DATABASE_ROOT_COLLECTION_USER).document(currentUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    UserReservation userReservation = new UserReservation();

                    String a = "";
                    final String name = document.getString("name");
                    for(int i =0; i < length; i++){
                        a += roomType[i];
                        userReservation.roomType = a;
                    }
                    userReservation.hotelName = hotelID;
                    userReservation.priceTotal = priceTotal;
                    userReservation.dateTime = date;
                    userReservation.name = name;

                    reservationList.add(userReservation);

                    storeToFS(name, hotelID, priceTotal, a, date);
                }
                reservationAdapter = new UserReservationAdapter(UserReservationActivity.this, reservationList);
                LinearLayoutManager LLM = new LinearLayoutManager(UserReservationActivity.this);
                content.setLayoutManager(LLM);
                content.setAdapter(reservationAdapter);
            }
        });
    }

    private void storeToFS(final String name, final String hotelID, final String priceTotal, final String a, final String date){

        UserReservation ur = new UserReservation(name, hotelID, date, priceTotal, a);

        fs.collection(DATABASE_ROOT_COLLECTION_RESERVATION).document(currentUser.getEmail())
                .set(ur)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserReservationActivity.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });


    }
}







