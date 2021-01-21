package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminManagement extends AppCompatActivity {

    private FirebaseFirestore fs;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_management);

        Globals globals = new Globals();
        globals.transStatus(getWindow());

        fs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        doSignout();

        doEdit();

    }

    /** Go page */

    private void doEdit(){
        Button hotel_mng = (Button) findViewById(R.id.hotel_mng_btn);

        hotel_mng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminManagement.this, HotelManageActivity.class);
                startActivity(intent);
            }
        });
    }

    /** Do sign out */
    private void doSignout(){
        TextView signOut = (TextView) findViewById(R.id.logout_link);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(AdminManagement.this, Home.class);
                startActivity(intent);
            }
        });
    }
}