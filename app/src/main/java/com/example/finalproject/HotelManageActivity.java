package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import android.content.DialogInterface;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelManageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private RecyclerView content;
    private List<Hotel> hotelList = new ArrayList<>();
    ;
    private HotelManageAdapter hotelAdapter;

    private static final String DATABASE_IMAGE_PATH = "hotelImages";
    private static final String DATABASE_ROOT_COLLECTION = "hotel_info";

    private FirebaseFirestore fs;
    private DatabaseReference hotelDBRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_manage);
        Globals globals = new Globals();
        globals.transStatus(getWindow());
        menuAction();


        fs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        hotelDBRef = FirebaseDatabase.getInstance().getReference(DATABASE_IMAGE_PATH);

        content = (RecyclerView) findViewById(R.id.contentView);

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.account_manage);
        menuItem.setTitle("Manage Account");

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        fs.collection(DATABASE_ROOT_COLLECTION).get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Hotel hotelInfo = new Hotel();

                            hotelInfo.hotelID = document.getId();
                            hotelInfo.hotelName = document.getString("name");
                            hotelInfo.numberAdd = document.getString("numberAddress");
                            hotelInfo.district = document.getString("district");
                            hotelInfo.ward = document.getString("ward");
                            hotelInfo.phone = document.getString("phone");
                            hotelInfo.special = document.getString("special");
                            hotelInfo.price = document.getString("priceRange");
                            hotelInfo.imageUrl = document.getString("imageUrl");
                            hotelInfo.deal = document.getString("deal");

                            hotelList.add(hotelInfo);

                        }

                        hotelAdapter = new HotelManageAdapter(HotelManageActivity.this, hotelList);
                        LinearLayoutManager LLM = new LinearLayoutManager(HotelManageActivity.this);
                        content.setLayoutManager(LLM);
                        content.setAdapter(hotelAdapter);
                    }
                }
            });

    }// setupRecyclerView end

    private void menuAction() {

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /**Tool Bar */
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        /**Navigation Drawer*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.header);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.account_manage:
                startActivity(new Intent(HotelManageActivity.this, AdminManagement.class));
                finish();
                break;
            case R.id.about_us:
                break;
            case R.id.rating_us:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}