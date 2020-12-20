package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity{


    @Override
        protected void onCreate(Bundle savedInstanceState){
        Globals gl = new Globals();

        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            onClick();
            transStatus();

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

    public void transStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}