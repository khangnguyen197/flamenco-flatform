package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class HotelDetail extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4, tv5;
    ImageView img;

    private FirebaseFirestore fs;


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

        fs = FirebaseFirestore.getInstance();

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);


        Intent intent = getIntent();
        String hotelID = intent.getStringExtra("hotelID");

        setupInfo(hotelID);

    }

    private void setupInfo(String hotelID){
        fs.collection("hotel_info").document(hotelID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    String name          = document.getString("name");
                    String numberAdd     = document.getString("numberAddress");
                    String district      = document.getString("district");
                    String ward          = document.getString("ward");
                    String phone         = document.getString("phone");
                    String special       = document.getString("special");
                    String price         = document.getString("priceRange");

                    tv1.setText(name);
                    tv2.setText("***");
                    tv3.setText("Address: "+numberAdd+", Dist. "+district+", Ward. "+ward);
                    tv4.setText("Phone: +"+phone);
                    tv5.setText("Price: "+price+"     "+"Special: "+special);

                }
            }
        });
    }
}