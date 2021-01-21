package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
        protected void onCreate(Bundle savedInstanceState){
        Globals globals = new Globals();

        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();

            /*if(currentUser != null){
                Intent homeIntent = new Intent(WelcomeActivity.this, Home.class);
                startActivity(homeIntent);
            }*/

            onClick();
            globals.transStatus(getWindow());


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