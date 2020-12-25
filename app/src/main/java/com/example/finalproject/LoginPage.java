package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Globals globals = new Globals();
        globals.transStatus(getWindow());
        onClick();
    }

    void onClick() {
        TextView signUpActive = (TextView) findViewById(R.id.sign_link);
        final TextView changePassActive = (TextView) findViewById(R.id.forget_pass);

        signUpActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signPage = new Intent(LoginPage.this, SignPage.class);
                startActivity(signPage);
            }
        });

        changePassActive.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                 setContentView(R.layout.change_pass_layout);
                 changePassActive();
             }
        });
    }

    void changePassActive(){
        Button resetPass = (Button) findViewById(R.id.reset_pass_btn) ;
        final TextView notice = (TextView) findViewById(R.id.reset_description);
        final TextView previous = (TextView) findViewById(R.id.return_label);

        resetPass.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                notice.setText(
                    "Please check your email and wait at least 60 seconds before sending your new reset request by pressing the reset button.");
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Intent returnPrevious = new Intent(LoginPage.this, LoginPage.class);
                startActivity(returnPrevious);
            }});
    }
    void onSubmit() {
        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        //if-else check if it is mapped to Firebase
        //if return true -> go Home
    }
}