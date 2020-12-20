package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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
        TextView signUpActivity = (TextView) findViewById(R.id.sign_link);
        signUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signPage = new Intent(LoginPage.this, SignPage.class);
                startActivity(signPage);
            }
        });
    }

    void onSubmit(){
        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        //if-else check if it is mapped to Firebase
        //if return true -> go Home
    }
}