package com.example.finalproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;

public class Globals extends AppCompatActivity {
    public void transStatus(Window w) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            w.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            w.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public String setText() {
        return "test";
    }
}
