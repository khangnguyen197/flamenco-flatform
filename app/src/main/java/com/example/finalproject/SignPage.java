package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);
        Globals globals = new Globals();
        globals.transStatus(getWindow());
        onClick();
    }

    void onClick() {
        TextView signUpActivity = (TextView) findViewById(R.id.login_link);
        signUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logPage = new Intent(SignPage.this, LoginPage.class);
                startActivity(logPage);
            }
        });
    }

    void onSubmit(){
        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        //if-else check if it is mapped to Firebase
        //if return true -> go Home
    }
}