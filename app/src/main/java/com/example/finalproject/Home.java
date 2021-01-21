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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import android.content.DialogInterface;
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

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private String isAdmin = null, mail = null;
    private RecyclerView content;
    private List<Hotel> hotelList = new ArrayList<>();
    ;
    private HotelAdapter hotelAdapter;
    public SearchView search;

    private String CODE_DEC_USER_ADMIN = "1";
    private Boolean DEC;

    private static final String DATABASE_ROOT_COLLECTION = "hotel_info";

    private FirebaseFirestore fs;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Globals globals = new Globals();
        globals.transStatus(getWindow());
        menuAction();

        fs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        content = (RecyclerView) findViewById(R.id.contentView);
        search = (SearchView) findViewById(R.id.search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                fs.collection(DATABASE_ROOT_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            clearAllData();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String district = document.getString("district");
                                if (newText.equals("")) {
                                    setupRecyclerView();
                                    break;
                                } else if (newText.equalsIgnoreCase(district)) {
                                    Hotel hotelInfo = new Hotel();
                                    List<Hotel> hotelList = new ArrayList<Hotel>();

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

                                    content.setHasFixedSize(true);
                                    hotelAdapter = new HotelAdapter(Home.this, hotelList);
                                    LinearLayoutManager LLM = new LinearLayoutManager(Home.this);
                                    content.setLayoutManager(LLM);
                                    content.setAdapter(hotelAdapter);
                                    break;
                                }
                            }
                        }
                    }
                });
                return false;
            }
        });

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.account_manage);

        Intent intent = getIntent();
        isAdmin = intent.getStringExtra("isAdmin");
        mail = intent.getStringExtra("mail");

        if (currentUser == null) {
            menuItem.setTitle("Sign Up / Sign In");
            DEC = true;
        }
        else if (isAdmin.equals(CODE_DEC_USER_ADMIN)) {
            menuItem.setTitle("Manage Account");
            DEC = false;
            Intent i = new Intent(getApplicationContext(), AdminManagement.class);
            startActivity(i);
            finish();
        } else{
            menuItem.setTitle("Manage Account");
            DEC = false;
        }

        clearAllData();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        fs.collection(DATABASE_ROOT_COLLECTION).get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        clearAllData();
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

                        hotelAdapter = new HotelAdapter(Home.this, hotelList);
                        LinearLayoutManager LLM = new LinearLayoutManager(Home.this);
                        content.setLayoutManager(LLM);
                        content.setAdapter(hotelAdapter);
                    }
                }
            });

    }// setupRecyclerView end

    public void clearAllData() {
        content.setHasFixedSize(true);
        content.setAdapter(null);
        content.setLayoutManager(null);
        hotelList.clear();
    }

    private void filterRoom(final String room) {
        fs.collection(DATABASE_ROOT_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    clearAllData();
                    for (final QueryDocumentSnapshot document1 : task.getResult()) {
                        fs.collection(DATABASE_ROOT_COLLECTION).document(document1.getId()).collection("roomType").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document2 : task.getResult()) {
                                            if (document2.getId().equalsIgnoreCase(room)) {
                                                Hotel hotelInfo = new Hotel();

                                                hotelInfo.hotelID = document1.getId();
                                                hotelInfo.hotelName = document1.getString("name");
                                                hotelInfo.numberAdd = document1.getString("numberAddress");
                                                hotelInfo.district = document1.getString("district");
                                                hotelInfo.ward = document1.getString("ward");
                                                hotelInfo.phone = document1.getString("phone");
                                                hotelInfo.special = document1.getString("special");
                                                hotelInfo.price = document1.getString("priceRange");
                                                hotelInfo.imageUrl = document1.getString("imageUrl");
                                                hotelInfo.deal = document1.getString("deal");

                                                hotelList.add(hotelInfo);

                                                hotelAdapter = new HotelAdapter(Home.this, hotelList);
                                                LinearLayoutManager LLM = new LinearLayoutManager(Home.this);
                                                content.setLayoutManager(LLM);
                                                content.setAdapter(hotelAdapter);

                                                break;
                                            }
                                        }
                                    }
                                }
                            });
                    }


                }
            }
        });
    }

    public void selectRoom(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CHOOSE YOUR ROOMS");

        final String[] selsem = {"NONE", "DELUXE", "DOUBLE", "SINGLE", "FAMILY", "PRESIDENT", "TEST"};
        builder.setItems(selsem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        setupRecyclerView();
                        break;
                    case 1:
                        filterRoom(selsem[1]);
                        break;
                    case 2:
                        filterRoom(selsem[2]);
                        break;
                    case 3:
                        filterRoom(selsem[3]);
                        break;
                    case 4:
                        filterRoom(selsem[4]);
                        break;
                    case 5:
                        filterRoom(selsem[5]);
                        break;
                    case 6:
                        filterRoom(selsem[6]);
                        break;
                    default:
                        break;

                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

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
                if (DEC) {
                    startActivity(new Intent(Home.this, LoginPage.class));
                    finish();
                } else if (isAdmin.equals(CODE_DEC_USER_ADMIN)){
                    startActivity(new Intent(Home.this, AdminManagement.class));
                    finish();
                } else{
                    Intent intent = new Intent(Home.this, UserInfo.class);
                    intent.putExtra("mail", mail);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.about_us:
                break;
            case R.id.rating_us:
                Intent intent1 = new Intent(Home.this, Rating.class);
                startActivity(intent1);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}