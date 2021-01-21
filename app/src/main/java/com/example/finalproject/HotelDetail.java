package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class HotelDetail extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4, tv5;
    ImageView img1, img2, img3, imageTest;

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

//        fs = FirebaseFirestore.getInstance();
//
//        tv1 = findViewById(R.id.tv1);
//        tv2 = findViewById(R.id.tv2);
//        tv3 = findViewById(R.id.tv3);
//        tv4 = findViewById(R.id.tv4);
//        tv5 = findViewById(R.id.tv5);

        imageTest = findViewById(R.id.imageView6);
//        imageTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(HotelDetail.this, "DIT ME CUOC DOI", Toast.LENGTH_SHORT).show();
//            }
//        });

//        img1 = findViewById(R.id.img1);
//        img2 = findViewById(R.id.img2);
//        img3 = findViewById(R.id.img3);

//        Intent intent = getIntent();
//        String hotelID = intent.getStringExtra("hotelID");
//
//        setupInfo(hotelID);
//        setupImage();

    }

    private void setupInfo(String hotelID) {
        fs.collection("hotel_info").document(hotelID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    String name = document.getString("name");
                    String numberAdd = document.getString("numberAddress");
                    String district = document.getString("district");
                    String ward = document.getString("ward");
                    String phone = document.getString("phone");
                    String special = document.getString("special");
                    String price = document.getString("priceRange");

                    tv1.setText(name);
                    tv2.setText("***");
                    tv3.setText("Address: " + numberAdd + ", Dist. " + district + ", Ward. " + ward);
                    tv4.setText("Phone: +" + phone);
                    tv5.setText("Price: " + price + "     " + "Special: " + special);

                }
            }
        });
    }

    private void setupImage() {
        fs.collection("hotel_info").document("Flamenco Go").collection("imageDetail").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 1; String url = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                 url = document.getString("imageUrl");

                                Log.e("ERROR", ": " + url);



                                // setupImage_2(url, i);
                            }
                        }
                    }
                });
    }

    private void setupImage_2(String url, int i){
        switch(i){
            case 1:
                Picasso.with(HotelDetail.this).load(url)
                        .fit()
                        .centerCrop()
                        .into(img1);
                Log.e("ERROR 1",": OK");
                break;
            case 2:
                Picasso.with(HotelDetail.this).load(url)
                        .fit()
                        .centerCrop()
                        .into(img2);
                break;
            case 3:
                Picasso.with(HotelDetail.this).load(url)
                        .fit()
                        .centerCrop()
                        .into(img3);
                break;
            default:
                break;
        }
    }
}
