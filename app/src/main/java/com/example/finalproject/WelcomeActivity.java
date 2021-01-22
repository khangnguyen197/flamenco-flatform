package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WelcomeActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore fs;

    @Override
        protected void onCreate(Bundle savedInstanceState){
        Globals globals = new Globals();

        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            fs =  FirebaseFirestore.getInstance();

            onClick();
            globals.transStatus(getWindow());


        }

   @Override
    protected void onStart() {
        super.onStart();
        if(currentUser != null){
           mAuth.signOut();
        }
    }

    private void onClick() {
            Button guestBtn = (Button) findViewById(R.id.guest_btn);
            Button signBtn = (Button) findViewById(R.id.sign_btn);

            guestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent homeIntent = new Intent(WelcomeActivity.this, Home.class);
                    startActivity(homeIntent);
                }
            });

            signBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(WelcomeActivity.this, LoginPage.class);
                    startActivity(loginIntent);
                }
            });
        }

}