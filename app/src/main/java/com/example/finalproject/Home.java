package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import android.content.DialogInterface;
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
import android.widget.Toast;

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

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
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
        menuAction();

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

    public void selectRoom(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CHOOSE YOUR ROOMS");

        String[] selsem = {"deluxe", "double", "single","family","president"};
        builder.setItems(selsem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(Home.this, "Deluxe room", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(Home.this, "Double room", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(Home.this, "Single room", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(Home.this, "Family room", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(Home.this, "President room", Toast.LENGTH_SHORT).show();
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
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
        switch (menuItem.getItemId()){
            case R.id.account_manage:
                Intent intent = new Intent(Home.this, LoginPage.class);
                startActivity(intent);
                break;
            case R.id.about_us:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}