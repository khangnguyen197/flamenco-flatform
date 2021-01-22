package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import javax.microedition.khronos.opengles.GL;

public class HotelDetail extends AppCompatActivity {

    Button btnSelectRoom, btnDeal; ImageButton btnBack;
    TextView tvHotelName, tvHotelSpecial, tvHotelPrice, tvHotelAddress, tvHotelPhone, lblHotelName;
    ImageView lgImg, subImg1, subImg2;

    private FirebaseFirestore fs;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        Globals globals = new Globals();
        globals.transStatus(getWindow());

        fs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();

        btnDeal = findViewById(R.id.hotel_deal);
        btnSelectRoom = findViewById(R.id.select_room);
        btnBack = findViewById(R.id.imageButton);

        lblHotelName = findViewById(R.id.hotel_name_lbl);
        tvHotelName = findViewById(R.id.hotel_name);
        tvHotelSpecial = findViewById(R.id.hotel_special);
        tvHotelPrice = findViewById(R.id.hotel_price);
        tvHotelAddress = findViewById(R.id.hotel_address);
        tvHotelPhone = findViewById(R.id.hotel_phone);

        lgImg = findViewById(R.id.lg_img);
        subImg1 = findViewById(R.id.sub_img_1);
        subImg2 = findViewById(R.id.sub_img_2);

        Intent intent = getIntent();
        final String hotelID = intent.getStringExtra("hotelID");
        final String isAdmin = intent.getStringExtra("isAdmin");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("isAdmin",isAdmin);
                startActivity(intent);
                finish();
            }
        });


        setupInfo(hotelID);
        setupImage(hotelID);

        btnSelectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUser != null){
                    Intent intent = new Intent(HotelDetail.this, RoomSelection.class);
                    intent.putExtra("hotelID", hotelID);
                    intent.putExtra("isAdmin",isAdmin);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(HotelDetail.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setupInfo(String hotelID) {
        fs.collection("hotel_info").document(hotelID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
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
                    String deal = document.getString("deal");

                    lblHotelName.setText(name);
                    tvHotelName.setText(name);
                    tvHotelAddress.setText(numberAdd+", "+district+" District, "+ward+" Ward");
                    tvHotelPhone.setText("+"+phone);
                    tvHotelSpecial.setText(special);
                    tvHotelPrice.setText(price);
                    btnDeal.setText(deal);

                }
            }
        });
    }

    private void setupImage(String hotelID) {
        fs.collection("hotel_info").document(hotelID).collection("imageDetail").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0; String url = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                url = document.getString("imageUrl");

                                setupImage_2(url, ++i);
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
                        .into(lgImg);
                break;
            case 2:
                Picasso.with(HotelDetail.this).load(url)
                        .fit()
                        .centerCrop()
                        .into(subImg1);
                break;
            case 3:
                Picasso.with(HotelDetail.this).load(url)
                        .fit()
                        .centerCrop()
                        .into(subImg2);
                break;
            default:
                break;
        }
    }
}