package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private  static final int IMAGE_CODE = 1;
    private RecyclerView content;
    private List<Hotel> hotelList = new ArrayList<>();;
    private HotelAdapter hotelAdapter;
    public SearchView search;

    private  static final String STORAGE_IMAGE_PATH = "hotel_Images";
    private  static final String DATABASE_IMAGE_PATH = "hotelImages";

    private Uri imageUri;
    private FirebaseFirestore fs;
   // private StorageReference hotelsrRef;
    private DatabaseReference hotelDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Globals globals = new Globals();
        globals.transStatus(getWindow());

        fs = FirebaseFirestore.getInstance();

       // hotelsrRef = FirebaseStorage.getInstance().getReference(STORAGE_IMAGE_PATH);
        hotelDBRef = FirebaseDatabase.getInstance().getReference(DATABASE_IMAGE_PATH);

        content = (RecyclerView) findViewById(R.id.contentView);
        search = (SearchView) findViewById(R.id.search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                fs.collection("hotel_info").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            clearAllData();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String district = document.getString("district");
                                if (newText.equals("")) {
                                    setupRecyclerView();
                                    break;
                                } else if (newText.equalsIgnoreCase(district)) {
                                    Hotel hotelInfo = new Hotel();
                                    List<Hotel> hotelList = new ArrayList<Hotel>();

                                    hotelInfo.hotelName = document.getString("name");
                                    hotelInfo.numberAdd = document.getString("numberAddress");
                                    hotelInfo.district = document.getString("district");
                                    hotelInfo.ward = document.getString("ward");
                                    hotelInfo.phone = document.getString("phone");
                                    hotelInfo.special = document.getString("special");
                                    hotelInfo.price = document.getString("priceRange");
                                    hotelInfo.imageUrl = document.getString("imageUrl");

                                    hotelList.add(hotelInfo);

                                    content.setHasFixedSize(true);
                                    hotelAdapter = new HotelAdapter(Home.this, hotelList);
                                    LinearLayoutManager LLM = new LinearLayoutManager(Home.this);
                                    content.setLayoutManager(LLM);
                                    content.setAdapter(hotelAdapter);
                                    break;
                                }
                            }
                        }}
                });
                return false;
            }
        });

        clearAllData();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        fs.collection("hotel_info").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    clearAllData();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Hotel hotelInfo = new Hotel();

                        hotelInfo.hotelName = document.getString("name");
                        hotelInfo.numberAdd = document.getString("numberAddress");
                        hotelInfo.district = document.getString("district");
                        hotelInfo.ward = document.getString("ward");
                        hotelInfo.phone = document.getString("phone");
                        hotelInfo.special = document.getString("special");
                        hotelInfo.price = document.getString("priceRange");
                        hotelInfo.imageUrl = document.getString("imageUrl");

                        hotelList.add(hotelInfo);

                    }

                    hotelAdapter = new HotelAdapter(Home.this, hotelList);
                    LinearLayoutManager LLM = new LinearLayoutManager(Home.this);
                    content.setLayoutManager(LLM);
                    content.setAdapter(hotelAdapter);
                }
            }
        });

    }// setupRecyclerView end

    public void clearAllData(){
        content.setHasFixedSize(true);
        content.setAdapter(null);
        content.setLayoutManager(null);
        hotelList.clear();
    }


    /*// get photo in your phone
    public void openGallery(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_CODE){
            imageUri = data.getData();
        }*/



}