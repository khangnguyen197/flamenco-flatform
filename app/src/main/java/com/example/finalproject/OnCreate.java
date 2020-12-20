package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.*;
public class OnCreate extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Globals globals = new Globals();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_create);
        globals.transStatus(getWindow());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(OnCreate.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }


};